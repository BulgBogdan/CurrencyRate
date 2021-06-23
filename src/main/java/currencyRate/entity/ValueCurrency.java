package currencyRate.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "currency_value", schema = "currency_rate")
public class ValueCurrency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "value")
    private String value;

    @ManyToOne
    @JoinColumn(name = "bank_branch_id")
    private BankBranch branch;

    @ManyToOne
    @JoinColumn(name = "currency_type_id")
    private TypeCurrency type;

    @ManyToOne
    @JoinColumn(name = "currency_select_id")
    private SelectCurrency select;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValueCurrency that = (ValueCurrency) o;
        return id == that.id &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value);
    }

    @Override
    public String toString() {
        return "ValueCurrency{" +
                "id=" + id +
                ", value='" + value + '\'' +
                '}';
    }
}