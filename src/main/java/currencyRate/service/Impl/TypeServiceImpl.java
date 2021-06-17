package currencyRate.service.Impl;

import currencyRate.entity.TypeCurrency;
import currencyRate.repository.TypeRepository;
import currencyRate.service.TypeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class TypeServiceImpl implements TypeService {

    private TypeRepository typeRepository;

    private static final Logger logger = Logger.getLogger(SelectServiceImpl.class);

    @Autowired
    public TypeServiceImpl(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    @Override
    public TypeCurrency add(TypeCurrency typeCurrency) {
        TypeCurrency type = typeRepository.save(typeCurrency);
        if (Objects.nonNull(type)) {
            logger.info(type + " successfully added. TypeCurrency : " + type);
        } else {
            logger.error("Error, TypeCurrency not added");
        }
        return type;
    }

    @Override
    public void delete(int id) {
        typeRepository.deleteById(id);
        logger.info("TypeCurrency deleted.");
    }

    @Override
    public TypeCurrency edit(TypeCurrency typeCurrency) {
        TypeCurrency type = typeRepository.saveAndFlush(typeCurrency);
        if (Objects.nonNull(type)) {
            logger.info(type + " successfully found. TypeCurrency : " + type);
        } else {
            logger.error("Error, TypeCurrency not found");
        }
        return type;
    }

    @Override
    public TypeCurrency getByName(String name) {
        TypeCurrency type = typeRepository.getByName(name);
        if (Objects.nonNull(type)) {
            logger.info(type + " successfully found. TypeCurrency : " + type);
        } else {
            logger.error("Error, TypeCurrency not found");
        }
        return type;
    }

    @Override
    public TypeCurrency getById(int id) {
        TypeCurrency type = typeRepository.getById(id);
        if (Objects.nonNull(type)) {
            logger.info(type + " successfully found. TypeCurrency : " + type);
        } else {
            logger.error("Error, TypeCurrency not found");
        }
        return type;
    }

    @Override
    public List<TypeCurrency> getAll() {
        List<TypeCurrency> types = typeRepository.findAll();
        if (types.isEmpty()) {
            logger.error("Error, TypeCurrency not founded");
        } else {
            for (TypeCurrency type : types) {
                logger.info(type + " successfully founded. TypeCurrency : " + type);
            }
        }
        return types;
    }

    @Override
    public Page<TypeCurrency> findPaginated(int page, int pageSize) {
        return null;
    }
}