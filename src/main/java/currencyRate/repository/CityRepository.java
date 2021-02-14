package currencyRate.repository;

import currencyRate.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {

    @Query("select c from City c where c.name = :cityName")
    City getByName(@Param("cityName") String cityName);
}