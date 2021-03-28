package currencyRate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CoursesController {

    private ModelAndView modelAndView = new ModelAndView();

    @GetMapping("/courses")
    public ModelAndView coursesPage() {
        modelAndView.setViewName("courses");
        return modelAndView;
    }
}
