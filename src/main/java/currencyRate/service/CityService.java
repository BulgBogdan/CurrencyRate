package currencyRate.service;

import currencyRate.entity.City;

import java.util.List;

public interface CityService {

    City add(City city);

    void delete(int id);

    City getById(int id);

    City edit(City city);

    List<City> getAll();
}
