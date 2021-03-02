package currencyRate.tasks.component;

import currencyRate.entity.*;
import currencyRate.service.*;
import currencyRate.tasks.banks.ParserDabrabyt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@PropertySource("classpath:banks.properties")
public class BankDabrabyt {

    @Value("${url.dabrabyt}")
    private String url;

    private BankService bankService;
    private BranchService branchService;
    private CityService cityService;
    private SelectService selectService;
    private TypeService typeService;
    private ValueService valueService;
    private ValueAndBranch valueAndBranch;

    @Autowired
    public BankDabrabyt(BankService bankService, BranchService branchService, CityService cityService,
                        SelectService selectService, TypeService typeService, ValueService valueService,
                        ValueAndBranch valueAndBranch) {
        this.bankService = bankService;
        this.branchService = branchService;
        this.cityService = cityService;
        this.selectService = selectService;
        this.typeService = typeService;
        this.valueService = valueService;
        this.valueAndBranch = valueAndBranch;
    }

    public void createAndUpdateValues() {
        Bank bank = bankService.getByName("Банк Дабрабыт");

        List<City> cities = cityService.getAll();
        List<SelectCurrency> selectCurrencies = selectService.getAll();
        List<TypeCurrency> typeCurrencies = typeService.getAll();

        List<BankBranch> filials = branchService.getBranchesByBankId(bank.getId());
        int i = 0;
        if (!filials.isEmpty()) {
            i = filials.get(0).getId();
        }
        for (City city : cities) {
            for (TypeCurrency typeCurrency : typeCurrencies) {
                for (SelectCurrency selectCurrency : selectCurrencies) {
                    Map<String, String> banksAndValues = ParserDabrabyt
                            .getValuesAndFilials(city.getName(), typeCurrency.getName(), selectCurrency.getSelect(), url);

                    String value = ParserDabrabyt.getBestValue(selectCurrency.getSelect(), banksAndValues);
                    String filialId = ParserDabrabyt.getFilialId(banksAndValues, value);

                    if (filials.isEmpty()) {
                        BankBranch branch = ParserDabrabyt.getFilial(bank, city, url, filialId);
                        branchService.add(branch);

                        ValueCurrency valueCurrency = valueAndBranch
                                .getValueCurrency(branch, selectCurrency, typeCurrency, value);
                        valueService.add(valueCurrency);
                    } else {
                        BankBranch branch = valueAndBranch.getEditBranch(branchService.getFilialById(i),
                                ParserDabrabyt.getFilial(bank, city, url, filialId));
                        branchService.edit(branch);

                        ValueCurrency valueCurrency = valueAndBranch.getEditValueCurrency(valueService.getById(i),
                                valueAndBranch.getValueCurrency(branch, selectCurrency, typeCurrency, value));
                        valueService.edit(valueCurrency);
                    }
                    i++;
                }
            }
        }
    }
}