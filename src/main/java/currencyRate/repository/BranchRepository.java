package currencyRate.repository;

import currencyRate.entity.BankBranch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchRepository extends JpaRepository<BankBranch, Integer> {

    @Query("select b from BankBranch b where b.name = :name")
    BankBranch getByFilialName(@Param("name") String name);

    @Query("select b from BankBranch b where b.id = :id")
    BankBranch getFilialById(@Param("id") int id);

    @Query("select branch from BankBranch branch join fetch branch.bank bank where bank.id = :id")
    List<BankBranch> getBranchesByBankId(@Param("id") int id);

}