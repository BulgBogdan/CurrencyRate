package currencyRate.tasks.component;

import currencyRate.entity.BankBranch;
import currencyRate.entity.SelectCurrency;
import currencyRate.entity.TypeCurrency;
import currencyRate.entity.ValueCurrency;
import org.springframework.stereotype.Component;

@Component
public class WorkValue {

    ValueCurrency getValueCurrency(BankBranch branch, SelectCurrency select, TypeCurrency type, String value) {
        ValueCurrency valueCurrency = new ValueCurrency();
        valueCurrency.setBranch(branch);
        valueCurrency.setSelect(select);
        valueCurrency.setType(type);
        valueCurrency.setValue(value);
        return valueCurrency;
    }
}