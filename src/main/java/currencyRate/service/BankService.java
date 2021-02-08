package currencyRate.service;

import currencyRate.entity.Bank;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BankService {

    Bank add(Bank bank);

    void delete(int id);

    Bank getByName(String bankName);

    Bank getById(int id);

    Bank edit(Bank bank);

    List<Bank> getAll();

    Page<Bank> findPaginated(int page, int pageSize);
}