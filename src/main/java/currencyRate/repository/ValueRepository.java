package currencyRate.repository;

import currencyRate.entity.ValueCurrency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValueRepository extends JpaRepository<ValueCurrency, Integer> {
}