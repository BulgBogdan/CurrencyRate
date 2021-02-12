package currencyRate.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = "currency_value", schema = "currency_rate")
public class ValueCurrency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "value")
    private double currencyValue;

    @ManyToOne
    @JoinColumn(name = "FK_currencySelect")
    private SelectCurrency selectCurrency;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValueCurrency valueCurrency = (ValueCurrency) o;
        return id == valueCurrency.id &&
                Double.compare(valueCurrency.currencyValue, currencyValue) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, currencyValue);
    }
}