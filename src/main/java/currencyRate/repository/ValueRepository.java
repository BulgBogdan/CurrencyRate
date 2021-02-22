package currencyRate.repository;

import currencyRate.entity.ValueCurrency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ValueRepository extends JpaRepository<ValueCurrency, Integer> {

    @Query("select v from ValueCurrency v where v.id = :id")
    ValueCurrency getById(@Param("id") int id);

}