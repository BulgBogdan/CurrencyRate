package currencyRate.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "bank", schema = "currency_rate")
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "bank_name")
    private String bankName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bank")
    private List<Type> types;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bank bank = (Bank) o;
        return id == bank.id &&
                Objects.equals(bankName, bank.bankName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bankName);
    }
}