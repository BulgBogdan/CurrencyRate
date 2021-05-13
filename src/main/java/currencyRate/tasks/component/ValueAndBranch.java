package currencyRate.tasks.component;

import currencyRate.entity.BankBranch;
import currencyRate.entity.SelectCurrency;
import currencyRate.entity.TypeCurrency;
import currencyRate.entity.ValueCurrency;
import org.springframework.stereotype.Component;

@Component
public class ValueAndBranch {

    ValueCurrency getValueCurrency(BankBranch branch, SelectCurrency select, TypeCurrency type, String value) {
        value = correctValue(value);
        ValueCurrency valueCurrency = new ValueCurrency();
        valueCurrency.setBranch(branch);
        valueCurrency.setSelect(select);
        valueCurrency.setType(type);
        valueCurrency.setValue(value);
        return valueCurrency;
    }

    BankBranch getEditBranch(BankBranch branch, BankBranch editBranch) {
        branch.setFilialId(editBranch.getFilialId());
        branch.setName(editBranch.getName());
        branch.setAddress(editBranch.getAddress());
        return branch;
    }

    ValueCurrency getEditValueCurrency(ValueCurrency valueCurrency, ValueCurrency editValueCurrency) {
        valueCurrency.setValue(editValueCurrency.getValue());
        return valueCurrency;
    }

    private String correctValue(String value) {
        if (!value.equalsIgnoreCase("Нет информации")) {
            value = substringNeedValue(value);
            double resultValue = Double.valueOf(value);
            if (resultValue < 1) {
                resultValue = resultValue * 100;
                value = String.valueOf(resultValue);
                value = substringNeedValue(value);
            }
        }
        return value;
    }

    private String substringNeedValue(String value) {
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