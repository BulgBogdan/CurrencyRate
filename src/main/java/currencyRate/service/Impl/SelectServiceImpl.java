package currencyRate.service.Impl;

import currencyRate.entity.SelectCurrency;
import currencyRate.repository.SelectRepository;
import currencyRate.service.SelectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SelectServiceImpl implements SelectService {

    private SelectRepository selectRepository;

    @Autowired
    public SelectServiceImpl(SelectRepository selectRepository) {
        this.selectRepository = selectRepository;
    }

    @Override
    public SelectCurrency add(SelectCurrency selectCurrency) {
        return selectRepository.saveAndFlush(selectCurrency);
    }

    @Override
    public void delete(int id) {
        selectRepository.deleteById(id);
    }

    @Override
    public SelectCurrency getById(int id) {
        return selectRepository.getOne(id);
    }

    @Override
    public SelectCurrency edit(SelectCurrency selectCurrency) {
        return selectRepository.saveAndFlush(selectCurrency);
    }

    @Override
    public List<SelectCurrency> getAll() {
        return selectRepository.findAll();
    }
}