package ru.sarmosov.deposit.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "deposits")
public class DepositEntity {

    @Id
    @Column(name = "id_deposit")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "deposit_account_id")
    private Long bankAccountId;

    @Column(name = "deposits_type_id")
    private int typeId;

    @Column(name = "deposit_refill")
    private boolean isRefillable;

    @Column(name = "deposits_amount")
    private BigDecimal balance;

    @Column(name = "start-date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "deposit_rate")
    private BigDecimal percent;

    @Column(name = "type_percent_payment_id")
    private int typePercentPaymentId;

    @Column(name = "percent_payment_date")
    private LocalDate percentPaymentDate;

    @Column(name = "percent_payment_account_id")
    private Long percentPaymentAccountId;

    @Column(name = "capitalization")
    private boolean isCapitalization;

    @Column(name = "is_withdrawable")
    private boolean isWithdrawable;

    @Column(name = "deposit_refund_account_id")
    private Long depositRefundAccountId;

    @Column(name = "customer_token")
    private String token;

}
