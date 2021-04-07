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

import java.util.ArrayList;
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
        String value = getBestValue(getValuesWithTypeAndSelect(typeService.getById(1), selectService.getById(1), cityService.getById(5)), selectService.getById(1));
        modelAndView.setViewName("home");
        return modelAndView;
    }

    private List<ValueCurrency> getValuesWithTypeAndSelect(TypeCurrency type, SelectCurrency select, City city) {
        List<ValueCurrency> values = valueService.getAll();
        List<ValueCurrency> valuesWithTypeAndSelect = new ArrayList<>();
        for (ValueCurrency value : values) {
            boolean cityEquals = city.equals(value.getBranch().getCity());
            boolean selectEquals = value.getSelect().equals(select);
            boolean typeEquals = value.getType().equals(type);
            if (cityEquals && selectEquals && typeEquals) {
                valuesWithTypeAndSelect.add(value);
            }
        }
        return valuesWithTypeAndSelect;
    }

    private String getBestValue(List<ValueCurrency> values, SelectCurrency select) {
        double getValue = Double.parseDouble(values.get(0).getValue());

        boolean sale = select.getSelect().equalsIgnoreCase("продажа");
        boolean buy = select.getSelect().equalsIgnoreCase("покупка");

        for (ValueCurrency value : values) {
            if (value.getValue().equals("")) {
                continue;
            } else {
                if (sale && (getValue >= Double.parseDouble(value.getValue()))) {
                    getValue = Double.parseDouble(value.getValue());
                }

                if (buy && (getValue <= Double.parseDouble(value.getValue()))) {
                    getValue = Double.parseDouble(value.getValue());
                }
            }
        }
        return String.valueOf(getValue);
    }

}