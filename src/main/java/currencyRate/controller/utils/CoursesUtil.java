package currencyRate.controller.utils;

import currencyRate.entity.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CoursesUtil {

    public static Set<Bank> listBanks(List<BankBranch> branches, City city) {
        Set<Bank> banks = new HashSet<>();
        for (BankBranch branch : branches) {
            if ((!branch.getName().equalsIgnoreCase("Нет информации")) && branch.getCity().equals(city)) {
                banks.add(branch.getBank());
            }
        }
        return banks;
    }

    public static List<BankBranch> filialsCityAndBank(Bank bank, City city, List<BankBranch> filials) {
        List<BankBranch> branches = new ArrayList<>();
        for (BankBranch filial : filials) {
            if (filial.getBank().equals(bank) && filial.getCity().equals(city)) {
                branches.add(filial);
            }
        }
        return branches;
    }

    public static ValueCurrency getValue(List<ValueCurrency> values, BankBranch filial, TypeCurrency type, String select) {
        ValueCurrency value = new ValueCurrency();
        for (ValueCurrency valueCurrency : values) {
            if (valueCurrency.getBranch().equals(filial)) {
                if (valueCurrency.getSelect().getSelect().equals(select) && valueCurrency.getType().equals(type)) {
                    value = valueCurrency;
                }
            }
        }
        return value;
    }

    public static String getCorrectValue(ValueCurrency valueCurrency) {
        String value = valueCurrency.getValue();
        if (!value.isEmpty()) {
            double doubleValue = Double.parseDouble(value);
            if (doubleValue < 1) {
                value = String.valueOf(doubleValue * 100);
            }
        }
        if (value.length() <= 5) {
            for (int i = value.length(); i <= 5; i++) {
                value = value + "0";
            }
        } else {
            value = value.substring(0, 6);
        }
        return value;
    }
}
