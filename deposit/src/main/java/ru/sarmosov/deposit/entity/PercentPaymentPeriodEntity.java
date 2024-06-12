package ru.sarmosov.deposit.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.sarmosov.bankstarter.enums.PercentPaymentPeriod;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "types_percent_payment")
public class PercentPaymentPeriodEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_type_percent_payment")
    private int id;

    @Column(name = "percent_payment_period_name")
    private String name;

    @Column(name = "percent_payment_period")
    private int period;

    public PercentPaymentPeriodEntity(PercentPaymentPeriod period) {
        this.name = period.name();
        this.period = period.getValue();
    }

}
