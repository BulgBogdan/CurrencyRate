package currencyRate.service.Impl;

import currencyRate.entity.BankBranch;
import currencyRate.repository.BranchRepository;
import currencyRate.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BranchServiceImpl implements BranchService {

    private BranchRepository branchRepository;

    @Autowired
    public BranchServiceImpl(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    @Override
    public BankBranch add(BankBranch bankBranch) {
        return branchRepository.save(bankBranch);
    }

    @Override
    public BankBranch edit(BankBranch bankBranch) {
        return branchRepository.saveAndFlush(bankBranch);
    }

    @Override
    public BankBranch getFilialById(int id) {
        return branchRepository.getFilialById(id);
    }

    @Override
    public List<BankBranch> getAll() {
        return branchRepository.findAll();
    }

    @Override
    public BankBranch getByFilialName(String name) {
        return branchRepository.getByFilialName(name);
    }

    @Override
    public List<BankBranch> getBranchesByBankId(int id) {
        return branchRepository.getBranchesByBankId(id);
    }
}