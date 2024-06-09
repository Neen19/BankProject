package ru.sarmosov.deposit.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.sarmosov.deposit.enums.PercentPaymentPeriod;
import ru.sarmosov.deposit.enums.PercentPaymentType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "types_percent_payment")
public class PercentPaymentTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_type_percent_payment")
    private int id;

    @Column(name = "type_percent_payment_period")
    @Enumerated(EnumType.STRING)
    private PercentPaymentPeriod period;

    @Column(name = "type_percent_payment_type")
    @Enumerated(EnumType.STRING)
    private PercentPaymentType percentPaymentType;

    public PercentPaymentTypeEntity(PercentPaymentPeriod period, PercentPaymentType percentPaymentType) {
        this.period = period;
        this.percentPaymentType = percentPaymentType;
    }

}
