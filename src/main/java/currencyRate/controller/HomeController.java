package currencyRate.controller;

import currencyRate.entity.SelectCurrency;
import currencyRate.entity.TypeCurrency;
import currencyRate.entity.ValueCurrency;
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

    private ValueService valueService;

    private TypeService typeService;

    private SelectService selectService;

    @Autowired
    public HomeController(ValueService valueService, TypeService typeService, SelectService selectService) {
        this.valueService = valueService;
        this.typeService = typeService;
        this.selectService = selectService;
    }

    @GetMapping("/")
    public ModelAndView homePage() {
        double value = getBestValues(typeService.getById(1), selectService.getById(1));
        modelAndView.setViewName("home");
        return modelAndView;
    }

    private double getBestValues(TypeCurrency typeCurrency, SelectCurrency selectCurrency) {
        List<ValueCurrency> values = valueService.getAll();
        double bestValue = Double.parseDouble(values.get(1).getValue());
        for (ValueCurrency value : values) {
            if (value.getSelect().getSelect().equals(selectCurrency.getSelect()) && value.getType().equals(typeCurrency)) {
                if (selectCurrency.getSelect().equalsIgnoreCase("продажа")) {
                    if (bestValue <= Double.parseDouble(value.getValue())) {
                        bestValue = Double.parseDouble(value.getValue());
                    }
                } else {
                    if (bestValue >= Double.parseDouble(value.getValue())) {
                        bestValue = Double.parseDouble(value.getValue());
                    }
                }
            }
        }
        return bestValue;
    }
}