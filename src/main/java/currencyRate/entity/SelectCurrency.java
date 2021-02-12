package currencyRate.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
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

    @Column(name = "currency_select")
    private String select;

    @Column(name = "currency_date")
    private Date currencyDate;

    @ManyToOne
    @JoinColumn(name = "FK_currencyType")
    private TypeCurrency typeCurrency;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "selectCurrency")
    private List<ValueCurrency> valueCurrencies;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SelectCurrency selectCurrency1 = (SelectCurrency) o;
        return id == selectCurrency1.id &&
                Objects.equals(select, selectCurrency1.select) &&
                Objects.equals(currencyDate, selectCurrency1.currencyDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, select, currencyDate);
    }
}