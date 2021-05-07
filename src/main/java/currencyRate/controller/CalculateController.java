package currencyRate.controller;

import currencyRate.controller.utils.CalculatorUtil;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class CalculateController {

    private ModelAndView modelAndView = new ModelAndView();

    private CityService cityService;

    private ValueService valueService;

    private TypeService typeService;

    private SelectService selectService;

    @Autowired
    public CalculateController(CityService cityService, ValueService valueService,
                               TypeService typeService, SelectService selectService) {
        this.cityService = cityService;
        this.valueService = valueService;
        this.typeService = typeService;
        this.selectService = selectService;
    }

    @GetMapping("/calculate/{city}")
    public ModelAndView calculatePage(@PathVariable("city") String city,
                                      @RequestParam(defaultValue = "USD") String type,
                                      @RequestParam(defaultValue = "Продажа") String select,
                                      @RequestParam(defaultValue = "100") String value) {
        List<City> cities = cityService.getAll();
        List<TypeCurrency> types = typeService.getAll();
        List<ValueCurrency> values = CalculatorUtil.getCityValues(valueService.getAll(), city);
        List<SelectCurrency> selects = selectService.getAll();
        List<String> sells = CalculatorUtil.getBestValues("Продажа", types, values);
        List<String> buys = CalculatorUtil.getBestValues("Покупка", types, values);
        String result = CalculatorUtil.calculateWithBestValue(values, value, select, type);
        String bestValue = CalculatorUtil.bestValue(values, select, type);

        modelAndView.addObject("result", result);
        modelAndView.addObject("bestValue", bestValue);
        modelAndView.addObject("cities", cities);
        modelAndView.addObject("selects", selects);
        modelAndView.addObject("types", types);
        modelAndView.addObject("sells", sells);
        modelAndView.addObject("buys", buys);
        modelAndView.addObject("city", city);
        modelAndView.addObject("type", type);
        modelAndView.addObject("select", select);
        modelAndView.addObject("value", value);
        modelAndView.setViewName("calculate");
        return modelAndView;
    }
}