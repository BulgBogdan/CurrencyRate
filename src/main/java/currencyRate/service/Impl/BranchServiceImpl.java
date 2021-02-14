package currencyRate.service.Impl;

import currencyRate.entity.BankBranch;
import currencyRate.repository.BranchRepository;
import currencyRate.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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
}
