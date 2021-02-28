package currencyRate.tasks.component;

import currencyRate.entity.*;
import currencyRate.service.*;
import currencyRate.tasks.banks.ParserBelagroprombank;
import currencyRate.tasks.parser.ParserXml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.xml.stream.XMLEventReader;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
@PropertySource("classpath:banks.properties")
public class Belagrpombank {

    @Value("${url.belagroprombank.list.banks}")
    private String urlBanks;

    @Value("${url.belagroprombank.currencies}")
    private String urlCurrency;

    private BankService bankService;
    private BranchService branchService;
    private CityService cityService;
    private SelectService selectService;
    private TypeService typeService;
    private ValueService valueService;
    private ValueAndBranch valueAndBranch;

    @Autowired
    public Belagrpombank(BankService bankService, BranchService branchService, CityService cityService,
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
        Bank bank = bankService.getByName("Белагропромбанк");

        List<City> cities = cityService.getAll();
        List<SelectCurrency> selectCurrencies = selectService.getAll();
        List<TypeCurrency> typeCurrencies = typeService.getAll();
        List<BankBranch> listFilials = branchService.getBranchesByBankId(bank.getId());

        XMLEventReader filialReader = ParserXml.getReader(urlBanks);
        int i = 0;
        if (!listFilials.isEmpty()) {
            i = listFilials.get(0).getId();
        }
        for (City city : cities) {
            String cityID = ParserBelagroprombank.getCityId(city.getName(), filialReader);
            for (TypeCurrency typeCurrency : typeCurrencies) {
                for (SelectCurrency selectCurrency : selectCurrencies) {

                    Map<String, String> banksAndValues = ParserBelagroprombank
                            .getBanksAndValues(cityID, typeCurrency.getName(), selectCurrency.getSelect(), urlCurrency);
                    String value = ParserBelagroprombank
                            .getUsefulValue(selectCurrency.getSelect(), banksAndValues);
                    Set<String> filials = ParserBelagroprombank
                            .getFilialId(value, banksAndValues);

                    if (!listFilials.isEmpty()) {
                        BankBranch branch = valueAndBranch.getEditBranch(branchService.getFilialById(i),
                                ParserBelagroprombank.getFilial(bank, city, filials, urlBanks));
                        branchService.edit(branch);

                        ValueCurrency valueCurrency = valueAndBranch.getEditValueCurrency(valueService.getById(i),
                                valueAndBranch.getValueCurrency(branch, selectCurrency, typeCurrency, value));
                        valueService.edit(valueCurrency);
                    } else {
                        BankBranch branch = ParserBelagroprombank
                                .getFilial(bank, city, filials, urlBanks);
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