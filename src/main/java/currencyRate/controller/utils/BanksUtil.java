package currencyRate.controller.utils;

import currencyRate.entity.TypeCurrency;
import currencyRate.entity.ValueCurrency;

import java.util.ArrayList;
import java.util.List;

public class BanksUtil {

    public static List<ValueCurrency> valuesSell(List<TypeCurrency> types, String city, String bank) {
        List<ValueCurrency> sells = new ArrayList<>();
        for (TypeCurrency type : types) {
            for (ValueCurrency value : type.getValues()) {
                boolean checkCityBank = value.getBranch().getBank().getName().equalsIgnoreCase(bank)
                        && value.getBranch().getCity().getName().equalsIgnoreCase(city);
                boolean checkTypeSelect = type.getName().equalsIgnoreCase(value.getType().getName())
                        && value.getSelect().getSelect().equalsIgnoreCase("продажа");
                if (checkCityBank && checkTypeSelect) {
                    sells.add(value);
                }
            }
        }
        return sells;
    }

    public static List<ValueCurrency> valuesBuy(List<TypeCurrency> types, String city, String bank) {
        List<ValueCurrency> buys = new ArrayList<>();
        for (TypeCurrency type : types) {
            for (ValueCurrency value : type.getValues()) {
                boolean checkCityBank = value.getBranch().getBank().getName().equalsIgnoreCase(bank)
                        && value.getBranch().getCity().getName().equalsIgnoreCase(city);
                boolean checkTypeSelect = type.getName().equalsIgnoreCase(value.getType().getName())
                        && value.getSelect().getSelect().equalsIgnoreCase("покупка");
                if (checkCityBank && checkTypeSelect) {
                    buys.add(value);
                }
            }
        }
        return buys;
    }
}
