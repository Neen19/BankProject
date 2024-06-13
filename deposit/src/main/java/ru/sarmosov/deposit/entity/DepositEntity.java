package ru.sarmosov.deposit.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.sarmosov.bankstarter.enums.DepositType;
import ru.sarmosov.bankstarter.enums.PercentPaymentPeriod;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "deposits")
public class DepositEntity {

    @Id
    @Column(name = "id_deposit")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_deposits_types", nullable = false)
    private DepositTypeEntity depositType;

    @Column(name = "deposits_amount")
    private BigDecimal balance;

    @Column(name = "start-date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "deposit_rate")
    private BigDecimal percent;

    @ManyToOne
    @JoinColumn(name = "id_type_percent_payment", nullable = false)
    private PercentPaymentPeriodEntity periodEntity;

    @Column(name = "percent_payment_date")
    private LocalDate percentPaymentDate;

    @Column(name = "capitalization")
    private boolean isCapitalization;

    @Column(name = "is_monthly")
    private boolean isMonthly;

    @Column(name = "customer_token")
    private String token;

    @Column(name = "customerId")
    private String customerId;

    public DepositEntity(
            DepositTypeEntity depositType,
            BigDecimal balance,
            LocalDate startDate,
            LocalDate endDate,
            BigDecimal percent,
            PercentPaymentPeriodEntity periodEntity,
            boolean isCapitalization,
            boolean isMonthly,
            String token,
            Long customerId) {
        this.depositType = depositType;
        this.balance = balance;
        this.startDate = startDate;
        this.endDate = endDate;
        this.percent = percent;
        this.periodEntity = periodEntity;
        this.isCapitalization = isCapitalization;
        this.isMonthly = isMonthly;
        this.token = token;
        this.percentPaymentDate = startDate.plusMonths(periodEntity.getPeriod().getValue());
        this.customerId = customerId.toString();
    }
}
