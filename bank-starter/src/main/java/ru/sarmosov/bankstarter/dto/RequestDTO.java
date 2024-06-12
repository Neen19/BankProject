package ru.sarmosov.bankstarter.dto;

import lombok.*;
import ru.sarmosov.bankstarter.enums.DepositType;
import ru.sarmosov.bankstarter.enums.PercentPaymentPeriod;
import ru.sarmosov.bankstarter.enums.PercentPaymentType;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RequestDTO {

    private BigDecimal amount;

    private DepositType depositType;

    private boolean isRefillable;

    private LocalDate endDate;

    private BigDecimal percent;

    private PercentPaymentType paymentType;

    private boolean isWithdrawal;

    private PercentPaymentPeriod paymentPeriod;


}
