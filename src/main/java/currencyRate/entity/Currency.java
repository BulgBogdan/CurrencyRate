package currencyRate.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Table(name = "currency", schema = "currency-rate")
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;

    @Column(name = "currency_date")
    Date currencyDate;

    @Column(name = "usd")
    double usd;

    @Column(name = "eur")
    double eur;

    @Column(name = "rub")
    double rub;
}
