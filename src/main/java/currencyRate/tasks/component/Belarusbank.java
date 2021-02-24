package currencyRate.tasks.component;

import currencyRate.entity.*;
import currencyRate.service.*;
import currencyRate.tasks.banks.ParserBelarusbank;
import currencyRate.tasks.parser.Parser;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@PropertySource("classpath:banks.properties")
public class Belarusbank {

    @Value("${url.belarusbank}")
    private String urlBank;

    private BankService bankService;
    private BranchService branchService;
    private CityService cityService;
    private SelectService selectService;
    private TypeService typeService;
    private ValueService valueService;
    private WorkValue workValue;

    @Autowired
    public Belarusbank(BankService bankService, BranchService branchService, CityService cityService,
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

    private String jsonText(String url) {
        return Parser.getInputText(url);
    }

    private JSONArray getJsonArray(String city) {
        String url = urlBank + city;
        return (JSONArray) JSONValue.parse(jsonText(url));
    }

    public void createAndUpdateValues() {
        Bank bank = bankService.getByName("Беларусбанк");

        List<City> cities = cityService.getAll();
        List<SelectCurrency> selectCurrencies = selectService.getAll();
        List<TypeCurrency> typeCurrencies = typeService.getAll();
        List<BankBranch> filials = branchService.getBranchesByBankId(bank.getId());

        String typeSelectedMoney;
        int i = 0;
        if (!filials.isEmpty()) {
            i = filials.get(0).getId();
        }
        for (City city : cities) {
            JSONArray jsonArray = getJsonArray(city.getName());
            for (TypeCurrency typeCurrency : typeCurrencies) {
                for (SelectCurrency selectCurrency : selectCurrencies) {
                    BankBranch branch = branchService.getFilialById(i);

                    typeSelectedMoney = ParserBelarusbank
                            .getTypeMoney(selectCurrency.getSelect(), typeCurrency.getName());

                    String value = ParserBelarusbank
                            .getUsefulValue(selectCurrency.getSelect(), typeSelectedMoney, jsonArray);

                    if (filials.isEmpty()) {
                        branch = ParserBelarusbank
                                .getFilial(bank, city, typeSelectedMoney, jsonArray, value);
                        branchService.add(branch);

                        ValueCurrency valueCurrency = workValue
                                .getValueCurrency(branch, selectCurrency, typeCurrency, value);
                        valueService.add(valueCurrency);
                    } else {
                        BankBranch branchEdit = ParserBelarusbank
                                .getFilial(bank, city, typeSelectedMoney, jsonArray, value);
                        branch.setFilialId(branchEdit.getFilialId());
                        branch.setName(branchEdit.getName());
                        branch.setAddress(branchEdit.getAddress());
                        branchService.edit(branch);

                        ValueCurrency valueCurrency = valueService.getById(i);
                        ValueCurrency valueEdit = workValue
                                .getValueCurrency(branch, selectCurrency, typeCurrency, value);
                        valueCurrency.setValue(valueEdit.getValue());
                        valueService.edit(valueCurrency);
                    }
                    i++;
                }
            }
        }
    }
}