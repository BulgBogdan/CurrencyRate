package currencyRate.service.Impl;

import currencyRate.entity.SelectCurrency;
import currencyRate.repository.SelectRepository;
import currencyRate.service.SelectService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class SelectServiceImpl implements SelectService {

    private SelectRepository selectRepository;

    private static final Logger logger = Logger.getLogger(SelectServiceImpl.class);


    @Autowired
    public SelectServiceImpl(SelectRepository selectRepository) {
        this.selectRepository = selectRepository;
    }

    @Override
    public SelectCurrency add(SelectCurrency selectCurrency) {
        SelectCurrency select = selectRepository.save(selectCurrency);
        if (Objects.nonNull(select)) {
            logger.info(select + " successfully added. SelectCurrency : " + select);
        } else {
            logger.error("Error, SelectCurrency not added");
        }
        return selectRepository.saveAndFlush(select);
    }

    @Override
    public void delete(int id) {
        selectRepository.deleteById(id);
        logger.info("SelectCurrency deleted.");
    }

    @Override
    public SelectCurrency getById(int id) {
        SelectCurrency select = selectRepository.getById(id);
        if (Objects.nonNull(select)) {
            logger.info(select + " successfully found. SelectCurrency : " + select);
        } else {
            logger.error("Error, SelectCurrency not found");
        }
        return select;
    }

    @Override
    public SelectCurrency edit(SelectCurrency selectCurrency) {
        SelectCurrency select = selectRepository.saveAndFlush(selectCurrency);
        if (Objects.nonNull(select)) {
            logger.info(select + " successfully edited. SelectCurrency select : " + select);
        } else {
            logger.error("Error, SelectCurrency select not edited");
        }
        return selectRepository.saveAndFlush(select);
    }

    @Override
    public List<SelectCurrency> getAll() {
        List<SelectCurrency> selectCurrencies = selectRepository.findAll();
        if (selectCurrencies.isEmpty()) {
            logger.error("Error, SelectCurrencies not founded");
        } else {
            for (SelectCurrency select : selectCurrencies) {
                logger.info(select + " successfully founded. SelectCurrency : " + select);
            }
        }
        return selectCurrencies;
    }
}