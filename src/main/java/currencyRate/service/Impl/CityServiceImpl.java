package currencyRate.service.Impl;

import currencyRate.entity.City;
import currencyRate.repository.CityRepository;
import currencyRate.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    private CityRepository cityRepository;

    @Autowired
    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public City add(City city) {
        return cityRepository.save(city);
    }

    @Override
    public void delete(int id) {
        cityRepository.deleteById(id);
    }

    @Override
    public City getById(int id) {
        return cityRepository.getOne(id);
    }

    @Override
    public City getByName(String cityName) {
        return cityRepository.getByName(cityName);
    }

    @Override
    public City edit(City city) {
        return cityRepository.saveAndFlush(city);
    }

    @Override
    public List<City> getAll() {
        return cityRepository.findAll();
    }
}
