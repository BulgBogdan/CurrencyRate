package currencyRate.repository;

import currencyRate.entity.BankBranch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchRepository extends JpaRepository<BankBranch, Integer> {

    @Query("select b from BankBranch b where b.name = :name")
    BankBranch getByFilialName(@Param("name") String name);

}
