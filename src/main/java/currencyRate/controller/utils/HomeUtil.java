package currencyRate.controller.utils;

import currencyRate.entity.City;
import currencyRate.entity.TypeCurrency;
import currencyRate.entity.ValueCurrency;

import java.util.ArrayList;
import java.util.List;

public class HomeUtil {

    public static String getBestValue(List<ValueCurrency> values, String select) {
        double getValue = Double.parseDouble(values.get(0).getValue());

        boolean sale = select.equalsIgnoreCase("продажа");
        boolean buy = select.equalsIgnoreCase("покупка");

        for (ValueCurrency value : values) {

            if (value.getValue().equals("") || value.getValue().equalsIgnoreCase("Нет информации")) {
                continue;
            } else {
                double doubleValue = Double.parseDouble(value.getValue());
                if (doubleValue < 1) {
                    doubleValue = doubleValue * 100;
                }
                if (sale && (getValue >= doubleValue)) {
                    getValue = doubleValue;
                }
                if (buy && (getValue <= doubleValue)) {
                    getValue = doubleValue;
                }
            }
        }
        return String.valueOf(getValue);
    }

    public static List<ValueCurrency> getValuesWithTypeAndSelect(TypeCurrency type,
                                                                 String selectMoney,
                                                                 City city,
                                                                 List<ValueCurrency> values) {
        List<ValueCurrency> valuesWithTypeAndSelect = new ArrayList<>();
        for (ValueCurrency value : values) {
            boolean cityEquals = city.equals(value.getBranch().getCity());
            boolean selectEquals = value.getSelect().getSelect().equalsIgnoreCase(selectMoney);
            boolean typeEquals = value.getType().equals(type);
            if (cityEquals && selectEquals && typeEquals) {
                valuesWithTypeAndSelect.add(value);
            }
        }
        return valuesWithTypeAndSelect;
    }

}