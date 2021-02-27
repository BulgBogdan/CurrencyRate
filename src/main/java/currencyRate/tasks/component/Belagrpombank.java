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
import java.util.Objects;
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
    private WorkValue workValue;

    @Autowired
    public Belagrpombank(BankService bankService, BranchService branchService, CityService cityService,
                         SelectService selectService, TypeService typeService, ValueService valueService,
                         WorkValue workValue) {
        this.bankService = bankService;
        this.branchService = branchService;
        this.cityService = cityService;
        this.selectService = selectService;
        this.typeService = typeService;
        this.valueService = valueService;
        this.workValue = workValue;
    }


    public void createAndUpdateValues() {
        Bank bank = bankService.getByName("Белагропромбанк");

        List<City> cities = cityService.getAll();
        List<SelectCurrency> selectCurrencies = selectService.getAll();
        List<TypeCurrency> typeCurrencies = typeService.getAll();

        XMLEventReader filialReader = ParserXml.getReader(urlBanks);
        int i = 37;
        for (City city : cities) {
            String cityID = ParserBelagroprombank.getCityId(city.getName(), filialReader);
            for (TypeCurrency typeCurrency : typeCurrencies) {
                for (SelectCurrency selectCurrency : selectCurrencies) {
                    BankBranch branch = branchService.getFilialById(i);

                    Map<String, String> banksAndValues = ParserBelagroprombank
                            .getBanksAndValues(cityID, typeCurrency.getName(), selectCurrency.getSelect(), urlCurrency);
                    String value = ParserBelagroprombank
                            .getUsefulValue(selectCurrency.getSelect(), banksAndValues);
                    Set<String> filials = ParserBelagroprombank
                            .getFilialId(value, banksAndValues);

                    if (Objects.nonNull(branch)) {
                        BankBranch branchEdit = ParserBelagroprombank
                                .getFilial(bank, city, filials, urlBanks);
                        branch.setFilialId(branchEdit.getFilialId());
                        branch.setName(branchEdit.getName());
                        branch.setAddress(branchEdit.getAddress());

                        branchService.edit(branch);
                    } else {
                        branch = ParserBelagroprombank
                                .getFilial(bank, city, filials, urlBanks);
                        branchService.add(branch);
                    }
                    ValueCurrency valueCurrency = workValue
                            .getValueCurrency(branch, selectCurrency, typeCurrency, value);
                    valueService.edit(valueCurrency);
                    i++;
                }
            }
        }
    }
}