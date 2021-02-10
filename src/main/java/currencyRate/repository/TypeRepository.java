package currencyRate.repository;

import currencyRate.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRepository extends JpaRepository<Type, Integer> {

    @Query("select t from Type t where t.name = :nameType")
    Type getByName(@Param("name") String nameType);
}