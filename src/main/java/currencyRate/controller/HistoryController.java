package currencyRate.controller;

import currencyRate.controller.utils.HistoryUtil;
import currencyRate.entity.TypeCurrency;
import currencyRate.service.TypeService;
import currencyRate.tasks.banks.NationalBank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HistoryController {

    private ModelAndView modelAndView = new ModelAndView();

    private TypeService typeService;

    private NationalBank nationalBank;

    @Autowired
    public HistoryController(TypeService typeService, NationalBank nationalBank) {
        this.typeService = typeService;
        this.nationalBank = nationalBank;
    }

    @GetMapping("/history/{city}")
    public ModelAndView historyPage(@PathVariable("city") String city,
                                    @RequestParam(required = false) Date date) {
        String selectDate = HistoryUtil.getDate(date);
        List<TypeCurrency> types = typeService.getAll();
        List<String> values = getValuesNBRB(selectDate);

        modelAndView.addObject("date", date);
        modelAndView.addObject("selectDate", selectDate);
        modelAndView.addObject("city", city);
        modelAndView.addObject("types", types);
        modelAndView.addObject("values", values);

        modelAndView.setViewName("history");
        return modelAndView;
    }

    private List<String> getValuesNBRB(String date) {
        List<String> values = new ArrayList<>();
        try {
            values.add(nationalBank.getValueUSD(date));
            values.add(nationalBank.getValueEUR(date));
            values.add(nationalBank.getValueRUB(date));
        } catch (NullPointerException e) {
            for (int i = values.size(); i <= 3; i++) {
                values.add("Нет информации на заданную дату");
            }
        }
        return values;
    }
}
