package currencyRate.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "currency_select", schema = "currency_rate")
public class Select {

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
    private Type type;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "select")
    private List<Value> values;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Select select1 = (Select) o;
        return id == select1.id &&
                Objects.equals(select, select1.select) &&
                Objects.equals(currencyDate, select1.currencyDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, select, currencyDate);
    }
}