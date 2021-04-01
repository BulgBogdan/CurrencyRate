package currencyRate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AboutController {

    private ModelAndView modelAndView = new ModelAndView();

    @GetMapping("/about")
    public ModelAndView aboutPage() {
        modelAndView.setViewName("about");
        return modelAndView;
    }
}
