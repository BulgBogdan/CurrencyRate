package currencyRate.service.Impl;

import currencyRate.entity.Bank;
import currencyRate.repository.BankRepository;
import currencyRate.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BankServiceImpl implements BankService {

    private BankRepository bankRepository;

    @Autowired
    public BankServiceImpl(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    @Override
    public Bank add(Bank bank) {
        return bankRepository.saveAndFlush(bank);
    }

    @Override
    public void delete(int id) {
        bankRepository.deleteById(id);
    }

    @Override
    public Bank getByName(String bankName) {
        return bankRepository.getByName(bankName);
    }

    @Override
    public Bank getById(int id) {
        return bankRepository.getOne(id);
    }

    @Override
    public Bank edit(Bank bank) {
        return bankRepository.saveAndFlush(bank);
    }

    @Override
    public List<Bank> getAll() {
        return bankRepository.findAll();
    }

    @Override
    public Page<Bank> findPaginated(int page, int pageSize) {
        return null;
    }
}