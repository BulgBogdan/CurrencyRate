package currencyRate.repository;

import currencyRate.entity.SelectCurrency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SelectRepository extends JpaRepository<SelectCurrency, Integer> {

    @Query("select s from SelectCurrency s where s.id = :id")
    SelectCurrency getById(@Param("id") int id);
}