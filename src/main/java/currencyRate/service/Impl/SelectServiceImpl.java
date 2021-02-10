package currencyRate.service.Impl;

import currencyRate.entity.Select;
import currencyRate.repository.SelectRepository;
import currencyRate.service.SelectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SelectServiceImpl implements SelectService {

    private SelectRepository selectRepository;

    @Autowired
    public SelectServiceImpl(SelectRepository selectRepository) {
        this.selectRepository = selectRepository;
    }

    @Override
    public Select add(Select select) {
        return selectRepository.saveAndFlush(select);
    }

    @Override
    public void delete(int id) {
        selectRepository.deleteById(id);
    }

    @Override
    public Select getById(int id) {
        return selectRepository.getOne(id);
    }

    @Override
    public Select edit(Select select) {
        return selectRepository.saveAndFlush(select);
    }
}