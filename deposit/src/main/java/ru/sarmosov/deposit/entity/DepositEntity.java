package ru.sarmosov.deposit.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.sarmosov.bankstarter.enums.DepositType;

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

    @Column(name = "period_percent_payment_id")
    private int periodPercentPaymentId;

    @Column(name = "percent_payment_date")
    private LocalDate percentPaymentDate;

    @Column(name = "capitalization")
    private boolean isCapitalization;

    @Column(name = "customer_token")
    private String token;

    public DepositEntity(int typeId, boolean isRefillable, BigDecimal balance, LocalDate startDate, LocalDate endDate, BigDecimal percent, int periodPercentPaymentId, LocalDate percentPaymentDate, boolean isCapitalization, boolean isWithdrawal, String token) {
        this.typeId = typeId;
        this.isRefillable = isRefillable;
        this.balance = balance;
        this.startDate = startDate;
        this.endDate = endDate;
        this.percent = percent;
        this.periodPercentPaymentId = periodPercentPaymentId;
        this.percentPaymentDate = percentPaymentDate;
        this.isCapitalization = isCapitalization;
        this.isWithdrawal = isWithdrawal;
        this.token = token;
    }
}
