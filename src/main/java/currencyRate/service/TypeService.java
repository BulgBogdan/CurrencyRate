package currencyRate.service;

import currencyRate.entity.Type;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TypeService {

    Type add(Type type);

    void delete(int id);

    Type edit(Type type);

    Type getByName(String name);

    Type getById(int id);

    List<Type> getAll();

    Page<Type> findPaginated(int page, int pageSize);
}