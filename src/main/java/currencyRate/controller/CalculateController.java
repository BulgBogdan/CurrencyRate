package currencyRate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CalculateController {

    private ModelAndView modelAndView = new ModelAndView();

    @GetMapping("/calculate")
    public ModelAndView homePage() {
        modelAndView.setViewName("calculate");
        return modelAndView;
    }
}