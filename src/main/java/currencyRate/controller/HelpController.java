package currencyRate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelpController {

    private ModelAndView modelAndView = new ModelAndView();

    @GetMapping("/help")
    public ModelAndView helpPage() {
        modelAndView.setViewName("help");
        return modelAndView;
    }
}
