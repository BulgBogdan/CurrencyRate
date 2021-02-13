package currencyRate.repository;

import currencyRate.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends JpaRepository<Bank, Integer> {

    @Query("select b from Bank b where b.name = :bankName")
    Bank getByName(@Param("bankName") String bankName);
}