package currencyRate.controller;

import currencyRate.controller.utils.CoursesUtil;
import currencyRate.entity.*;
import currencyRate.service.BranchService;
import currencyRate.service.CityService;
import currencyRate.service.TypeService;
import currencyRate.service.ValueService;
import currencyRate.tasks.banks.NationalBank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class CoursesController {

    private ModelAndView modelAndView = new ModelAndView();

    private CityService cityService;

    private ValueService valueService;

    private TypeService typeService;

    private BranchService branchService;

    private NationalBank nationalBank;

    @Autowired
    public CoursesController(CityService cityService, ValueService valueService, TypeService typeService,
                             NationalBank nationalBank, BranchService branchService) {
        this.cityService = cityService;
        this.valueService = valueService;
        this.typeService = typeService;
        this.nationalBank = nationalBank;
        this.branchService = branchService;
    }

    @GetMapping("/courses/{city}")
    public ModelAndView coursesPage(@PathVariable("city") String city,
                                    @RequestParam(defaultValue = "USD") String type) {
        modelAndView.addObject("city", city);
        modelAndView.addObject("type", type);

        City selectedCity = cityService.getByName(city);
        List<City> cities = cityService.getAll();
        List<TypeCurrency> types = typeService.getAll();
        List<BankBranch> filials = branchService.getAll();
        Set<Bank> banks = CoursesUtil.listBanks(filials, selectedCity);
        modelAndView.addObject("cities", cities);
        modelAndView.addObject("banks", banks);
        modelAndView.addObject("types", types);

        TypeCurrency typeCurrency = typeService.getByName(type);
        List<ValueCurrency> values = valueService.getAll();
        List<String> valuesSell = new ArrayList<>();
        List<String> valuesBuy = new ArrayList<>();
        for (Bank bank : banks) {
            for (BankBranch branch : CoursesUtil.filialsCityAndBank(bank, selectedCity, filials)) {
                ValueCurrency valueSell = CoursesUtil.getValue(values, branch, typeCurrency, "Продажа");
                ValueCurrency valueBuy = CoursesUtil.getValue(values, branch, typeCurrency, "Покупка");
                if (branch.equals(valueSell.getBranch())) {
                    valuesSell.add(CoursesUtil.getCorrectValue(valueSell));
                } else if (branch.equals(valueBuy.getBranch())) {
                    valuesBuy.add(CoursesUtil.getCorrectValue(valueBuy));
                }
            }
        }
        modelAndView.addObject("valuesSell", valuesSell);
        modelAndView.addObject("valuesBuy", valuesBuy);

        if (type.equalsIgnoreCase("usd")) {
            modelAndView.addObject("nbrb", nationalBank.getValueUSD(nationalBank.today()));
        } else if (type.equalsIgnoreCase("eur")) {
            modelAndView.addObject("nbrb", nationalBank.getValueEUR(nationalBank.today()));
        } else if (type.equalsIgnoreCase("rub")) {
            modelAndView.addObject("nbrb", nationalBank.getValueRUB(nationalBank.today()));
        }
        modelAndView.setViewName("courses");
        return modelAndView;
    }
}
