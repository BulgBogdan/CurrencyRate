package currencyRate.service.Impl;

import currencyRate.entity.Value;
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
    public Value add(Value value) {
        return valueRepository.saveAndFlush(value);
    }

    @Override
    public void delete(int id) {
        valueRepository.deleteById(id);
    }

    @Override
    public Value getById(int id) {
        return valueRepository.getOne(id);
    }

    @Override
    public Value edit(Value value) {
        return valueRepository.saveAndFlush(value);
    }

    @Override
    public List<Value> getAll() {
        return valueRepository.findAll();
    }

    @Override
    public Page<Value> findPaginated(int page, int pageSize) {
        return null;
    }
}
