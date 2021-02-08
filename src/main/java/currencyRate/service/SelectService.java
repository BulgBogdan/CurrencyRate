package currencyRate.service;

import currencyRate.entity.Select;

public interface SelectService {

    Select add(Select select);

    void delete(int id);

    Select getById(int id);

    Select edit(Select select);
}