package currencyRate.service.Impl;

import currencyRate.entity.ValueCurrency;
import currencyRate.repository.ValueRepository;
import currencyRate.service.ValueService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class ValueServiceImpl implements ValueService {

    private ValueRepository valueRepository;

    private static final Logger logger = Logger.getLogger(ValueServiceImpl.class);

    @Autowired
    public ValueServiceImpl(ValueRepository valueRepository) {
        this.valueRepository = valueRepository;
    }

    @Override
    public ValueCurrency add(ValueCurrency valueCurrency) {
        ValueCurrency value = valueRepository.save(valueCurrency);
        if (Objects.nonNull(value)) {
            logger.info(value + " successfully added. ValueCurrency : " + value);
        } else {
            logger.error("Error, ValueCurrency not added");
        }
        return value;
    }

    @Override
    public void delete(int id) {
        valueRepository.deleteById(id);
        logger.info("ValueCurrency deleted.");
    }

    @Override
    public ValueCurrency getById(int id) {
        ValueCurrency value = valueRepository.getById(id);
        if (Objects.nonNull(value)) {
            logger.info(value + " successfully found. ValueCurrency : " + value);
        } else {
            logger.error("Error, ValueCurrency not found");
        }
        return value;
    }

    @Override
    public ValueCurrency edit(ValueCurrency valueCurrency) {
        ValueCurrency value = valueRepository.saveAndFlush(valueCurrency);
        if (Objects.nonNull(value)) {
            logger.info(value + " successfully edit. ValueCurrency : " + value);
        } else {
            logger.error("Error, ValueCurrency not edit");
        }
        return value;
    }

    @Override
    public List<ValueCurrency> getAll() {
        List<ValueCurrency> values = valueRepository.findAll();
        if (values.isEmpty()) {
            logger.error("Error, ValueCurrency not founded");
        } else {
            for (ValueCurrency value : values) {
                logger.info(value + " successfully founded. ValueCurrency : " + value);
            }
        }
        return values;
    }

    @Override
    public Page<ValueCurrency> findPaginated(int page, int pageSize) {
        return null;
    }
}
