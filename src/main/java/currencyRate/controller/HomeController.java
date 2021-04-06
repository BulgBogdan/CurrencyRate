package currencyRate.controller;

import currencyRate.entity.City;
import currencyRate.entity.SelectCurrency;
import currencyRate.entity.TypeCurrency;
import currencyRate.entity.ValueCurrency;
import currencyRate.service.CityService;
import currencyRate.service.SelectService;
import currencyRate.service.TypeService;
import currencyRate.service.ValueService;
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

    private SelectService selectService;

    @Autowired
    public HomeController(ValueService valueService, TypeService typeService,
                          SelectService selectService, CityService cityService) {
        this.valueService = valueService;
        this.typeService = typeService;
        this.selectService = selectService;
        this.cityService = cityService;
    }

    @GetMapping("/")
    public ModelAndView homePage() {
        String value = getBestValues(typeService.getById(1), selectService.getById(1), cityService.getById(5));
        modelAndView.setViewName("home");
        return modelAndView;
    }

    private String getBestValues(TypeCurrency typeCurrency, SelectCurrency selectCurrency, City city) {
        List<ValueCurrency> values = valueService.getAll();
        String bestValue = "";
        double valueBest = Double.parseDouble(values.get(1).getValue());
        for (ValueCurrency value : values) {
            if (city.getName().equals(value.getBranch().getCity().getName())) {
                if (value.getSelect().getSelect().equals(selectCurrency.getSelect()) && value.getType().equals(typeCurrency)) {
                    if (selectCurrency.getSelect().equalsIgnoreCase("продажа")) {
                        if (valueBest <= Double.parseDouble(value.getValue())) {
                            bestValue = value.getValue();
                        }
                    } else {
                        if (valueBest >= Double.parseDouble(value.getValue())) {
                            bestValue = value.getValue();
                        }
                    }
                }
            }
        }
        return bestValue;
    }

}