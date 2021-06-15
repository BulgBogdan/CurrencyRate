package currencyRate.service.Impl;

import currencyRate.entity.BankBranch;
import currencyRate.repository.BranchRepository;
import currencyRate.service.BranchService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class BranchServiceImpl implements BranchService {

    private BranchRepository branchRepository;

    private static final Logger logger = Logger.getLogger(BranchServiceImpl.class);

    @Autowired
    public BranchServiceImpl(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    @Override
    public BankBranch add(BankBranch bankBranch) {
        BankBranch createdBranch = branchRepository.saveAndFlush(bankBranch);
        if (Objects.nonNull(createdBranch)) {
            logger.info(createdBranch + " successfully added. BankBranch : " + createdBranch);
        } else {
            logger.error("Error, BankBranch not added");
        }
        return createdBranch;
    }

    @Override
    public BankBranch edit(BankBranch bankBranch) {
        BankBranch editBranch = branchRepository.saveAndFlush(bankBranch);
        if (Objects.nonNull(editBranch)) {
            logger.info(editBranch + " successfully edited. BankBranch : " + editBranch);
        } else {
            logger.error("Error, BankBranch not edited");
        }
        return editBranch;

    }

    @Override
    public BankBranch getFilialById(int id) {
        BankBranch getBranch = branchRepository.getFilialById(id);
        if (Objects.nonNull(getBranch)) {
            logger.info(getBranch + " successfully found. BankBranch : " + getBranch);
        } else {
            logger.error("Error, BankBranch not found");
        }
        return getBranch;
    }

    @Override
    public List<BankBranch> getAll() {
        List<BankBranch> branches = branchRepository.findAll();
        if (branches.isEmpty()) {
            logger.error("Error, BankBranches not founded");
        } else {
            for (BankBranch branch : branches) {
                logger.info(branch + " successfully founded. BankBranch : " + branch);
            }
        }
        return branches;
    }

    @Override
    public BankBranch getByFilialName(String name) {
        BankBranch getBranch = branchRepository.getByFilialName(name);
        if (Objects.nonNull(getBranch)) {
            logger.info(getBranch + " successfully found. BankBranch : " + getBranch);
        } else {
            logger.error("Error, BankBranch not found");
        }
        return getBranch;
    }

    @Override
    public List<BankBranch> getBranchesByBankId(int id) {
        List<BankBranch> branches = branchRepository.getBranchesByBankId(id);
        if (branches.isEmpty()) {
            logger.error("Error, BankBranches not founded");
        } else {
            for (BankBranch branch : branches) {
                logger.info(branch + " successfully founded. BankBranch : " + branch);
            }
        }
        return branches;
    }
}