package currencyRate.tasks.component;

import currencyRate.entity.*;
import currencyRate.service.*;
import currencyRate.tasks.banks.ParserAbsolutebank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Component
@PropertySource("classpath:banks.properties")
public class Absolutebank {

    @Value("${url.absolutbank}")
    private String url;

    private BankService bankService;
    private BranchService branchService;
    private CityService cityService;
    private SelectService selectService;
    private TypeService typeService;
    private ValueService valueService;
    private ValueAndBranch valueAndBranch;

    @Autowired
    public Absolutebank(BankService bankService, BranchService branchService, CityService cityService,
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
        Bank bank = bankService.getByName("Абсолютбанк");

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
                    System.out.println("nachalo " + LocalTime.now().toString());
                    Map<String, String> filialsAndValues = ParserAbsolutebank
                            .getFilialAndValue(city.getName(), typeCurrency.getName(), selectCurrency.getSelect(), url);
                    String value = ParserAbsolutebank.getBestValue(filialsAndValues, selectCurrency.getSelect());
                    System.out.println("seredina " + LocalTime.now().toString());
                    if (!listFilials.isEmpty()) {
                        BankBranch branch = valueAndBranch.getEditBranch(branchService.getFilialById(i),
                                ParserAbsolutebank.getBranch(city, bank, filialsAndValues, value, url));
                        branchService.edit(branch);

                        ValueCurrency valueCurrency = valueAndBranch.getEditValueCurrency(valueService.getById(i),
                                valueAndBranch.getValueCurrency(branch, selectCurrency, typeCurrency, value));
                        valueService.edit(valueCurrency);
                    } else {
                        BankBranch branch = ParserAbsolutebank.getBranch(city, bank, filialsAndValues, value, url);
                        branchService.add(branch);

                        ValueCurrency valueCurrency = valueAndBranch
                                .getValueCurrency(branch, selectCurrency, typeCurrency, value);
                        valueService.add(valueCurrency);
                    }
                    System.out.println("konec " + LocalTime.now().toString());
                    i++;
                }
            }
        }
    }
}
