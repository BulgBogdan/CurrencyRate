package currencyRate.controller;

import currencyRate.tasks.component.BankDabrabyt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalTime;

@Controller
@RequestMapping("/")
public class HomePageController {

    private ModelAndView modelAndView = new ModelAndView();

    @Autowired
    BankDabrabyt bankDabrabyt;

    @GetMapping("/")
    public ModelAndView homePage() {
        System.out.println("start - " + LocalTime.now().toString());
        bankDabrabyt.createAndUpdateValues();
        System.out.println("finish - " + LocalTime.now().toString());
        modelAndView.setViewName("home");
        System.out.println("after set view - " + LocalTime.now().toString());
        return modelAndView;
    }
}