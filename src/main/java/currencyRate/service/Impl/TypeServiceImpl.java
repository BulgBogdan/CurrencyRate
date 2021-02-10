package currencyRate.service.Impl;

import currencyRate.entity.Type;
import currencyRate.repository.TypeRepository;
import currencyRate.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    private TypeRepository typeRepository;

    @Autowired
    public TypeServiceImpl(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    @Override
    public Type add(Type type) {
        return typeRepository.saveAndFlush(type);
    }

    @Override
    public void delete(int id) {
        typeRepository.deleteById(id);
    }

    @Override
    public Type edit(Type type) {
        return typeRepository.saveAndFlush(type);
    }

    @Override
    public Type getByName(String name) {
        return typeRepository.getByName(name);
    }

    @Override
    public Type getById(int id) {
        return typeRepository.getOne(id);
    }

    @Override
    public List<Type> getAll() {
        return typeRepository.findAll();
    }

    @Override
    public Page<Type> findPaginated(int page, int pageSize) {
        return null;
    }
}