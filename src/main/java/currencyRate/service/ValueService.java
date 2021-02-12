package currencyRate.service;

import currencyRate.entity.ValueCurrency;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ValueService {

    ValueCurrency add(ValueCurrency valueCurrency);

    void delete(int id);

    ValueCurrency getById(int id);

    ValueCurrency edit(ValueCurrency valueCurrency);

    List<ValueCurrency> getAll();

    Page<ValueCurrency> findPaginated(int page, int pageSize);
}