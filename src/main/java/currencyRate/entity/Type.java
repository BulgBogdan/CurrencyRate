package currencyRate.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "currency_type", schema = "currency_rate")
public class Type {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "currency_name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "FK_bank")
    private Bank bank;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "type")
    private List<Select> selects;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Type type = (Type) o;
        return id == type.id &&
                Objects.equals(name, type.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}