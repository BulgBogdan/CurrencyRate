package currencyRate.controller;

import currencyRate.controller.logic.HomeMethods;
import currencyRate.entity.TypeCurrency;
import currencyRate.entity.ValueCurrency;
import currencyRate.service.CityService;
import currencyRate.service.TypeService;
import currencyRate.service.ValueService;
import currencyRate.tasks.banks.NationalBank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
public class HomeController {

    private ModelAndView modelAndView = new ModelAndView();

    private CityService cityService;

    private ValueService valueService;

    private TypeService typeService;

    private NationalBank nationalBank;

    @Autowired
    public HomeController(ValueService valueService, TypeService typeService,
                          CityService cityService, NationalBank nationalBank) {
        this.valueService = valueService;
        this.typeService = typeService;
        this.cityService = cityService;
        this.nationalBank = nationalBank;
    }

    @GetMapping("/")
    public ModelAndView homePage() {
        modelAndView.addObject("saleUSD", valueByType("usd", "продажа"));
        modelAndView.addObject("saleEUR", valueByType("eur", "продажа"));
        modelAndView.addObject("saleRUB", valueByType("rub", "продажа"));
        modelAndView.addObject("buyUSD", valueByType("usd", "покупка"));
        modelAndView.addObject("buyEUR", valueByType("eur", "покупка"));
        modelAndView.addObject("buyRUB", valueByType("rub", "покупка"));
        modelAndView.addObject("nbrbUSD", nationalBank.getValueUSD());
        modelAndView.addObject("nbrbEUR", nationalBank.getValueEUR());
        modelAndView.addObject("nbrbRUB", nationalBank.getValueRUB());
        modelAndView.setViewName("home");
        return modelAndView;
    }

    private String valueByType(String typeMoney, String selectMoney) {
        String value = "";
        List<TypeCurrency> types = typeService.getAll();
        for (TypeCurrency type : types) {
            List<ValueCurrency> values = HomeMethods
                    .getValuesWithTypeAndSelect(type, selectMoney, cityService.getById(5), valueService.getAll());
            if (type.getName().equalsIgnoreCase(typeMoney)) {
                value = HomeMethods.getBestValue(values, selectMoney);
                break;
            }
        }
        return value;
    }
}