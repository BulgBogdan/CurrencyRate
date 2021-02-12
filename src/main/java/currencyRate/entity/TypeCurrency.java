package currencyRate.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "currency_type", schema = "currency_rate")
public class TypeCurrency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "currency_name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "FK_bank")
    private Bank bank;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "typeCurrency")
    private List<SelectCurrency> selectCurrencies;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypeCurrency typeCurrency = (TypeCurrency) o;
        return id == typeCurrency.id &&
                Objects.equals(name, typeCurrency.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}