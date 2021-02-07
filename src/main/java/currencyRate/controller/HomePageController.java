package currencyRate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class HomePageController {

    private ModelAndView modelAndView = new ModelAndView();

    @GetMapping("/")
    public ModelAndView homePage() {
        modelAndView.setViewName("home");
        return modelAndView;
    }
}