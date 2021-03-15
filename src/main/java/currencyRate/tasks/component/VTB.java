package currencyRate.tasks.component;

import currencyRate.entity.*;
import currencyRate.service.*;
import currencyRate.tasks.banks.ParserVTB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@PropertySource("classpath:banks.properties")
public class VTB {

    @Value("${url.vtd}")
    private String url;

    private BankService bankService;
    private BranchService branchService;
    private CityService cityService;
    private SelectService selectService;
    private TypeService typeService;
    private ValueService valueService;
    private ValueAndBranch valueAndBranch;

    @Autowired
    public VTB(BankService bankService, BranchService branchService, CityService cityService,
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
        Bank bank = bankService.getByName("ВТБ Банк");

        List<City> cities = cityService.getAll();
        List<SelectCurrency> selectCurrencies = selectService.getAll();
        List<TypeCurrency> typeCurrencies = typeService.getAll();
        List<BankBranch> listFilials = branchService.getBranchesByBankId(bank.getId());

        int i = 0;
        if (!listFilials.isEmpty()) {
            i = listFilials.get(0).getId();
        }
        for (City city : cities) {
            for (TypeCurrency typeCurrency : typeCurrencies) {
                for (SelectCurrency selectCurrency : selectCurrencies) {
                    String value = ParserVTB.getValue(typeCurrency.getName(), selectCurrency.getSelect(), url);

                    if (!listFilials.isEmpty()) {
                        BankBranch branch = valueAndBranch.getEditBranch(branchService.getFilialById(i),
                                ParserVTB.getBranch(city, bank));
                        branchService.edit(branch);

                        ValueCurrency valueCurrency = valueAndBranch.getEditValueCurrency(valueService.getById(i),
                                valueAndBranch.getValueCurrency(branch, selectCurrency, typeCurrency, value));
                        valueService.edit(valueCurrency);
                    } else {
                        BankBranch branch = ParserVTB.getBranch(city, bank);
                        branchService.add(branch);

                        ValueCurrency valueCurrency = valueAndBranch
                                .getValueCurrency(branch, selectCurrency, typeCurrency, value);
                        valueService.add(valueCurrency);
                    }
                    i++;
                }
            }
        }
    }
}