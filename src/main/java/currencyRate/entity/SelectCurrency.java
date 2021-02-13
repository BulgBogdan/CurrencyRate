package currencyRate.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "currency_select", schema = "currency_rate")
public class SelectCurrency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "select")
    private String select;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "select")
    private List<ValueCurrency> values;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SelectCurrency that = (SelectCurrency) o;
        return id == that.id &&
                Objects.equals(select, that.select);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, select);
    }
}