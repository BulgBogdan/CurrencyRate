package currencyRate.repository;

import currencyRate.entity.SelectCurrency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SelectRepository extends JpaRepository<SelectCurrency, Integer> {
}