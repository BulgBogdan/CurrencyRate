package currencyRate.service;

import currencyRate.entity.Value;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ValueService {

    Value add(Value value);

    void delete(int id);

    Value getById(int id);

    Value edit(Value value);

    List<Value> getAll();

    Page<Value> findPaginated(int page, int pageSize);
}