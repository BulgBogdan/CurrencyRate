package currencyRate.service;

import currencyRate.entity.TypeCurrency;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TypeService {

    TypeCurrency add(TypeCurrency typeCurrency);

    void delete(int id);

    TypeCurrency edit(TypeCurrency typeCurrency);

    TypeCurrency getByName(String name);

    TypeCurrency getById(int id);

    List<TypeCurrency> getAll();

    Page<TypeCurrency> findPaginated(int page, int pageSize);
}