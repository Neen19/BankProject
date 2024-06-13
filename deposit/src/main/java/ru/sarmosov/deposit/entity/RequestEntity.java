package ru.sarmosov.deposit.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.util.Pair;
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

    @Column(name = "request_date")
    private LocalDate requestDate;

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

    @Column(name = "deposit_percent")
    private BigDecimal percent;

    @Column(name = "percent_payment_period")
    private PercentPaymentPeriod period;

    @Column(name = "is_capitalization")
    private boolean isCapitalization;

    @Column(name = "is_monthly")
    private boolean isMonthly;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "request_description")
    private String description;

    public RequestEntity
            (BigDecimal amount,
             RequestStatusEntity status,
             String token,
             DepositType depositType,
             BigDecimal percent,
             PercentPaymentPeriod period,
             Long customerId,
             PercentPaymentType paymentType,
             Boolean isCapitalization,
             String description) {

        this.requestDate = LocalDate.now();
        this.amount = amount;
        this.status = status;
        this.token = token;
        this.depositType = depositType;
        this.percent = percent;
        this.period = period;
        this.customerId = customerId;
        this.description = description;
        if (paymentType.equals(PercentPaymentType.AT_THE_END))
            this.isMonthly = false;
        else isMonthly = true;
        this.isCapitalization = isCapitalization;
    }

    @Override
    public String toString() {
        return "RequestEntity{" +
                "id=" + id +
                ", requestDate=" + requestDate +
                ", amount=" + amount +
                ", status=" + status +
                ", token='" + token + '\'' +
                ", depositType=" + depositType +
                ", percent=" + percent +
                ", period=" + period +
                ", isCapitalization=" + isCapitalization +
                ", isMonthly=" + isMonthly +
                '}';
    }
}
