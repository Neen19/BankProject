package ru.sarmosov.deposit.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;
import ru.sarmosov.bankstarter.dto.CustomerDTO;
import ru.sarmosov.bankstarter.dto.RequestDTO;
import ru.sarmosov.bankstarter.enums.DepositType;
import ru.sarmosov.bankstarter.enums.PercentPaymentPeriod;
import ru.sarmosov.bankstarter.enums.PercentPaymentType;
import ru.sarmosov.bankstarter.enums.RequestStatus;


import java.math.BigDecimal;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "requests")
public class RequestEntity {


    @Id
    @Column(name = "id_request")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "request_date")
    private LocalDate requestDate;

    @Column(name = "deposit_id")
    private Long depositId;

    @Column(name = "deposit_amount")
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "id_request_status", nullable = false)
    private RequestStatusEntity status;


    @Column(name = "customer_token")
    private String token;

    @Enumerated(EnumType.STRING)
    @Column(name = "deposit_type")
    private DepositType depositType;
    @Column(name = "is_refillable")
    private boolean isRefillable;
    @Transient
    private LocalDate endDate;
    @Column(name = "deposit_percent")
    private BigDecimal percent;
    @Transient
    private PercentPaymentType paymentType;
    @Column(name = "is_withdrawal")
    private boolean isWithdrawal;
    @Transient
    private PercentPaymentPeriod period;
    @Transient
    private LocalDate paymentDate;
    @Transient
    private Long accountId;
    @Column(name = "customer_email")
    private String email;




    public RequestEntity(RequestDTO request, CustomerDTO customer, String token, RequestStatusEntity status) {
        customerId = customer.getId();
        requestDate = LocalDate.now();
        depositId = null;
        this.status = status;
        amount = request.getAmount();
        this.token = token;
        this.depositType = request.getDepositType();
        this.isRefillable = request.isRefillable();
        this.endDate = request.getEndDate();
        this.percent = request.getPercent();
        this.paymentType = request.getPaymentType();
        this.isWithdrawal = request.isWithdrawal();
        this.period = request.getPaymentPeriod();
        accountId = customer.getBankAccountId();
        email = customer.getPhoneNumber();
        if (paymentType.equals(PercentPaymentType.AT_THE_END))
            paymentDate = LocalDate.now().plusMonths(period.getValue());
        else paymentDate = LocalDate.now().plusMonths(1);
    }


    public RequestEntity(Long customerId, LocalDate requestDate, Long depositId, BigDecimal amount, RequestStatusEntity status) {
        this.customerId = customerId;
        this.requestDate = requestDate;
        this.depositId = depositId;
        this.status = status;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "RequestEntity{" + "\n" +
                "id=" + id + "\n" +
                ", customerId=" + customerId + "\n" +
                ", requestDate=" + requestDate + "\n" +
                ", depositId=" + depositId + "\n" +
                ", amount=" + amount + "\n" +
                ", status=" + status + "\n" +
                ", token='" + token + '\'' + "\n" +
                ", depositType=" + depositType + "\n" +
                ", isRefillable=" + isRefillable + "\n" +
                ", endDate=" + endDate + "\n" +
                ", percent=" + percent + "\n" +
                ", paymentType=" + paymentType + "\n" +
                ", isWithdrawal=" + isWithdrawal + "\n" +
                ", period=" + period + "\n" +
                ", paymentDate=" + paymentDate + "\n" +
                ", accountId=" + accountId + "\n" +
                ", email='" + email + '\'' + "\n" +
                '}';
    }
}
