package currencyRate.controller;

import currencyRate.controller.utils.HomeUtil;
import currencyRate.entity.City;
import currencyRate.entity.TypeCurrency;
import currencyRate.entity.ValueCurrency;
import currencyRate.service.CityService;
import currencyRate.service.TypeService;
import currencyRate.service.ValueService;
import currencyRate.tasks.banks.NationalBank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ModelAndView homePage(@RequestParam(defaultValue = "Минск") String city) {
        List<City> cities = cityService.getAll();
        modelAndView.addObject("cities", cities);
        modelAndView.addObject("city", city);
        modelAndView.addObject("saleUSD", valueByType("usd", "продажа", city));
        modelAndView.addObject("saleEUR", valueByType("eur", "продажа", city));
        modelAndView.addObject("saleRUB", valueByType("rub", "продажа", city));
        modelAndView.addObject("buyUSD", valueByType("usd", "покупка", city));
        modelAndView.addObject("buyEUR", valueByType("eur", "покупка", city));
        modelAndView.addObject("buyRUB", valueByType("rub", "покупка", city));
        modelAndView.addObject("nbrbUSD", nationalBank.getValueUSD(nationalBank.today()));
        modelAndView.addObject("nbrbEUR", nationalBank.getValueEUR(nationalBank.today()));
        modelAndView.addObject("nbrbRUB", nationalBank.getValueRUB(nationalBank.today()));
        modelAndView.setViewName("home");
        return modelAndView;
    }

    private String valueByType(String typeMoney, String selectMoney, String city) {
        String value = "";
        List<TypeCurrency> types = typeService.getAll();
        for (TypeCurrency type : types) {
            List<ValueCurrency> values = HomeUtil
                    .getValuesWithTypeAndSelect(type, selectMoney, cityService.getByName(city), valueService.getAll());
            if (type.getName().equalsIgnoreCase(typeMoney)) {
                value = HomeUtil.getBestValue(values, selectMoney);
                break;
            }
        }
        return value;
    }
}