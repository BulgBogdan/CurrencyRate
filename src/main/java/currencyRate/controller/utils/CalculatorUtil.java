package currencyRate.controller.utils;

import currencyRate.entity.TypeCurrency;
import currencyRate.entity.ValueCurrency;

import java.util.ArrayList;
import java.util.List;

public class CalculatorUtil {

    private static String bestValue(List<ValueCurrency> values, String select, String type) {
        double getValue = 0;

        boolean sale = select.equalsIgnoreCase("продажа");
        boolean buy = select.equalsIgnoreCase("покупка");
        int count = 0;
        for (ValueCurrency value : values) {
            boolean typeConfirm = value.getType().getName().equalsIgnoreCase(type);
            if (typeConfirm) {
                double doubleValue = Double.parseDouble(value.getValue());
                count++;
                if (count == 1) {
                    getValue = doubleValue;
                }
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

    public static List<ValueCurrency> getCityValues(List<ValueCurrency> values, String city) {
        List<ValueCurrency> listValues = new ArrayList<>();
        for (ValueCurrency value : values) {
            boolean cityConfirm = value.getBranch().getCity().getName().equalsIgnoreCase(city);
            boolean emptyOrNull = value.getValue().isEmpty()
                    || value.getValue().equalsIgnoreCase("Нет информации");
            if (cityConfirm && (!emptyOrNull)) {
                listValues.add(value);
            }
        }
        return listValues;
    }

    public static List<String> getBestValues(String select, List<TypeCurrency> typesCurrency, List<ValueCurrency> values) {
        List<String> listValues = new ArrayList<>();
        String value = "";
        for (TypeCurrency type : typesCurrency) {
            value = bestValue(values, select, type.getName());
            listValues.add(value);
        }
        return listValues;
    }

}
