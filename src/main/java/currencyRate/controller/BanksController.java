package currencyRate.controller;

import currencyRate.controller.utils.BanksUtil;
import currencyRate.entity.TypeCurrency;
import currencyRate.entity.ValueCurrency;
import currencyRate.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class BanksController {

    private ModelAndView modelAndView = new ModelAndView();

    private TypeService typeService;

    @Autowired
    public BanksController(TypeService typeService) {
        this.typeService = typeService;
    }

    @GetMapping("/banks/{city}/{bank}")
    public ModelAndView banksPage(@PathVariable("city") String city,
                                  @PathVariable("bank") String bank) {
        List<TypeCurrency> types = typeService.getAll();
        List<ValueCurrency> sells = BanksUtil.valuesSell(types, city, bank);
        List<ValueCurrency> buys = BanksUtil.valuesBuy(types, city, bank);

        modelAndView.addObject("sells", sells);
        modelAndView.addObject("buys", buys);
        modelAndView.addObject("types", types);
        modelAndView.addObject("bank", bank);
        modelAndView.addObject("city", city);
        modelAndView.setViewName("banks");
        return modelAndView;
    }
}
