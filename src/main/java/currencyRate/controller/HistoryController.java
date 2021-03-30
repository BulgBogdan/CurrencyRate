package currencyRate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HistoryController {

    private ModelAndView modelAndView = new ModelAndView();

    @GetMapping("/history")
    public ModelAndView historyPage() {
        modelAndView.setViewName("history");
        return modelAndView;
    }
}
