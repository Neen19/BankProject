package ru.sarmosov.deposit.dto;

import lombok.*;
import ru.sarmosov.bankstarter.enums.DepositType;
import ru.sarmosov.bankstarter.enums.PercentPaymentPeriod;
import ru.sarmosov.deposit.entity.RequestStatusEntity;

import java.math.BigDecimal;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RequestResponseDTO {

    private Long id;

    private LocalDate requestDate;

    private BigDecimal amount;

    private RequestStatusEntity status;

    private String token;

    private DepositType depositType;

    private BigDecimal percent;

    private PercentPaymentPeriod period;

    private boolean isCapitalization;

    private boolean isMonthly;

}
