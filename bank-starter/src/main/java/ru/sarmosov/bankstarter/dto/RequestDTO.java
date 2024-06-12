package ru.sarmosov.bankstarter.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jdk.jfr.BooleanFlag;
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
@ToString
public class RequestDTO {

    @PositiveOrZero(message = "balance can't be negative")
    @NotBlank(message = "balance can't be blank")
    private BigDecimal amount;

    @NotNull(message = "The field must not be null")
    private boolean isRefillable;

    @PositiveOrZero(message = "percent can't be negative")
    @NotBlank(message = "percent can't be blank")
    private BigDecimal percent;

    @NotBlank(message = "payment type can't be null")
    private PercentPaymentType paymentType;

    @NotNull(message = "The field must not be null")
    private boolean isWithdrawal;

    @NotBlank(message = "payment period can't be blank")
    private PercentPaymentPeriod paymentPeriod;


}
