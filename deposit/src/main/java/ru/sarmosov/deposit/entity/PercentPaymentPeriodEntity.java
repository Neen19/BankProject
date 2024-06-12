package ru.sarmosov.deposit.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.sarmosov.bankstarter.enums.PercentPaymentPeriod;
import ru.sarmosov.deposit.converter.PercentPaymentPeriodConverter;

import java.util.Set;

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

    @Convert(converter = PercentPaymentPeriodConverter.class)
    private PercentPaymentPeriod period;

    @OneToMany(mappedBy = "id")
    private Set<DepositEntity> deposits;

    public PercentPaymentPeriodEntity(PercentPaymentPeriod period) {
        this.period = period;
    }


}
