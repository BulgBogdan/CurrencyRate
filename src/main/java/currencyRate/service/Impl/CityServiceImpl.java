package currencyRate.service.Impl;

import currencyRate.entity.City;
import currencyRate.repository.CityRepository;
import currencyRate.service.CityService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class CityServiceImpl implements CityService {

    private CityRepository cityRepository;

    private static final Logger logger = Logger.getLogger(CityServiceImpl.class);

    @Autowired
    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public City add(City city) {
        City createdCity = cityRepository.save(city);
        if (Objects.nonNull(createdCity)) {
            logger.info(createdCity + " successfully added. City : " + createdCity);
        } else {
            logger.error("Error, City not added");
        }
        return createdCity;
    }

    @Override
    public void delete(int id) {
        cityRepository.deleteById(id);
        logger.info("City deleted.");
    }

    @Override
    public City getById(int id) {
        City getCity = cityRepository.getById(id);
        if (Objects.nonNull(getCity)) {
            logger.info(getCity + " successfully found. City : " + getCity);
        } else {
            logger.error("Error, City not found");
        }
        return getCity;
    }

    @Override
    public City getByName(String cityName) {
        City getCity = cityRepository.getByName(cityName);
        if (Objects.nonNull(getCity)) {
            logger.info(getCity + " successfully found. City : " + getCity);
        } else {
            logger.error("Error, City not found");
        }
        return getCity;
    }

    @Override
    public City edit(City city) {
        City editedCity = cityRepository.saveAndFlush(city);
        if (Objects.nonNull(editedCity)) {
            logger.info(editedCity + " successfully edited. City : " + editedCity);
        } else {
            logger.error("Error, City not edited");
        }
        return editedCity;
    }

    @Override
    public List<City> getAll() {
        List<City> cities = cityRepository.findAll();
        if (cities.isEmpty()) {
            logger.error("Error, Cities not founded");
        } else {
            for (City city : cities) {
                logger.info(city + " successfully founded. City : " + city);
            }
        }
        return cities;
    }
}
