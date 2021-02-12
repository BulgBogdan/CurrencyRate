package currencyRate.service.Impl;

import currencyRate.entity.ValueCurrency;
import currencyRate.repository.ValueRepository;
import currencyRate.service.ValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ValueServiceImpl implements ValueService {

    private ValueRepository valueRepository;

    @Autowired
    public ValueServiceImpl(ValueRepository valueRepository) {
        this.valueRepository = valueRepository;
    }

    @Override
    public ValueCurrency add(ValueCurrency valueCurrency) {
        return valueRepository.saveAndFlush(valueCurrency);
    }

    @Override
    public void delete(int id) {
        valueRepository.deleteById(id);
    }

    @Override
    public ValueCurrency getById(int id) {
        return valueRepository.getOne(id);
    }

    @Override
    public ValueCurrency edit(ValueCurrency valueCurrency) {
        return valueRepository.saveAndFlush(valueCurrency);
    }

    @Override
    public List<ValueCurrency> getAll() {
        return valueRepository.findAll();
    }

    @Override
    public Page<ValueCurrency> findPaginated(int page, int pageSize) {
        return null;
    }
}
