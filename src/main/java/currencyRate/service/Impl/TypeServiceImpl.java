package currencyRate.service.Impl;

import currencyRate.entity.TypeCurrency;
import currencyRate.repository.TypeRepository;
import currencyRate.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TypeServiceImpl implements TypeService {

    private TypeRepository typeRepository;

    @Autowired
    public TypeServiceImpl(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    @Override
    public TypeCurrency add(TypeCurrency typeCurrency) {
        return typeRepository.saveAndFlush(typeCurrency);
    }

    @Override
    public void delete(int id) {
        typeRepository.deleteById(id);
    }

    @Override
    public TypeCurrency edit(TypeCurrency typeCurrency) {
        return typeRepository.saveAndFlush(typeCurrency);
    }

    @Override
    public TypeCurrency getByName(String name) {
        return typeRepository.getByName(name);
    }

    @Override
    public TypeCurrency getById(int id) {
        return typeRepository.getOne(id);
    }

    @Override
    public List<TypeCurrency> getAll() {
        return typeRepository.findAll();
    }

    @Override
    public Page<TypeCurrency> findPaginated(int page, int pageSize) {
        return null;
    }
}