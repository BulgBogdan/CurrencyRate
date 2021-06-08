package currencyRate.service.Impl;

import currencyRate.entity.Bank;
import currencyRate.repository.BankRepository;
import currencyRate.service.BankService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class BankServiceImpl implements BankService {

    private BankRepository bankRepository;

    private static final Logger logger = Logger.getLogger(BankServiceImpl.class);

    @Autowired
    public BankServiceImpl(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    @Override
    public Bank add(Bank bank) {
        Bank createdBank = bankRepository.saveAndFlush(bank);
        if (Objects.nonNull(createdBank)) {
            logger.info(createdBank + " successfully added. Bank : " + createdBank);
        } else {
            logger.error("Error, Bank not added");
        }
        return createdBank;
    }

    @Override
    public void delete(int id) {
        bankRepository.deleteById(id);
        logger.info("Bank deleted.");
    }

    @Override
    public Bank getByName(String bankName) {
        Bank getBank = bankRepository.getByName(bankName);
        if (Objects.nonNull(getBank)) {
            logger.info(getBank + " successfully found. Bank : " + getBank);
        } else {
            logger.error("Error, Bank not found");
        }
        return getBank;
    }

    @Override
    public Bank getById(int id) {
        Bank getBank = bankRepository.getById(id);
        if (Objects.nonNull(getBank)) {
            logger.info(getBank + " successfully found. Bank : " + getBank);
        } else {
            logger.error("Error, Bank not found");
        }
        return getBank;
    }

    @Override
    public Bank edit(Bank bank) {
        Bank editedBank = bankRepository.saveAndFlush(bank);
        if (Objects.nonNull(editedBank)) {
            logger.info(editedBank + " successfully edited. Bank : " + editedBank);
        } else {
            logger.error("Error, Bank not edited");
        }
        return editedBank;
    }

    @Override
    public List<Bank> getAll() {
        List<Bank> banks = bankRepository.findAll();
        if (banks.isEmpty()) {
            logger.error("Error, Banks not founded");
        } else {
            for (Bank bank : banks) {
                logger.info(bank + " successfully founded. Bank : " + bank);
            }
        }
        return banks;
    }

    @Override
    public Page<Bank> findPaginated(int page, int pageSize) {
        return null;
    }
}