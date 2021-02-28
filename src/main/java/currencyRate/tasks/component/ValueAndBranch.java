package currencyRate.tasks.component;

import currencyRate.entity.BankBranch;
import currencyRate.entity.SelectCurrency;
import currencyRate.entity.TypeCurrency;
import currencyRate.entity.ValueCurrency;
import org.springframework.stereotype.Component;

@Component
public class ValueAndBranch {

    ValueCurrency getValueCurrency(BankBranch branch, SelectCurrency select, TypeCurrency type, String value) {
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
}