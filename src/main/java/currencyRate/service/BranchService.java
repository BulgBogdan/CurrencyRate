package currencyRate.service;

import currencyRate.entity.BankBranch;

import java.util.List;

public interface BranchService {

    BankBranch add(BankBranch bankBranch);

    BankBranch edit(BankBranch bankBranch);

    BankBranch getFilialById(int id);

    BankBranch getByFilialName(String name);

    List<BankBranch> getAll();

    List<BankBranch> getBranchesByBankId(int id);

}