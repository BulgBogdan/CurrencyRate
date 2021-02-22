package currencyRate.repository;

import currencyRate.entity.TypeCurrency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRepository extends JpaRepository<TypeCurrency, Integer> {

    @Query("select t from TypeCurrency t where t.id = :id")
    TypeCurrency getById(@Param("id") int id);

    @Query("select t from TypeCurrency t where t.name = :nameType")
    TypeCurrency getByName(@Param("name") String nameType);
}