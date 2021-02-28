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
    private ValueAndBranch valueAndBranch;

    @Autowired
    public Belarusbank(BankService bankService, BranchService branchService, CityService cityService,
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
                    typeSelectedMoney = ParserBelarusbank
                            .getTypeMoney(selectCurrency.getSelect(), typeCurrency.getName());

                    String value = ParserBelarusbank
                            .getUsefulValue(selectCurrency.getSelect(), typeSelectedMoney, jsonArray);

                    if (filials.isEmpty()) {
                        BankBranch branch = ParserBelarusbank
                                .getFilial(bank, city, typeSelectedMoney, jsonArray, value);
                        branchService.add(branch);

                        ValueCurrency valueCurrency = valueAndBranch
                                .getValueCurrency(branch, selectCurrency, typeCurrency, value);
                        valueService.add(valueCurrency);
                    } else {
                        BankBranch branch = valueAndBranch.getEditBranch(branchService.getFilialById(i),
                                ParserBelarusbank.getFilial(bank, city, typeSelectedMoney, jsonArray, value));
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