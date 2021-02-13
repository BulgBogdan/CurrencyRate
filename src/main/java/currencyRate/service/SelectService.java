package currencyRate.service;

import currencyRate.entity.SelectCurrency;

import java.util.List;

public interface SelectService {

    SelectCurrency add(SelectCurrency selectCurrency);

    void delete(int id);

    SelectCurrency getById(int id);

    SelectCurrency edit(SelectCurrency selectCurrency);

    List<SelectCurrency> getAll();
}