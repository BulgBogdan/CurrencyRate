package currencyRate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BanksController {

    private ModelAndView modelAndView = new ModelAndView();

    @GetMapping("/banks")
    public ModelAndView banksPage() {
        modelAndView.setViewName("banks");
        return modelAndView;
    }
}
