package ru.sarmosov.deposit.dto;

import lombok.*;
import ru.sarmosov.bankstarter.enums.PercentPaymentPeriod;
import ru.sarmosov.deposit.entity.DepositTypeEntity;
import ru.sarmosov.deposit.entity.PercentPaymentPeriodEntity;

import java.math.BigDecimal;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DepositDTO {

    private Long id;

    private DepositTypeEntity depositType;

    private BigDecimal balance;

    private LocalDate startDate;

    private LocalDate endDate;

    private BigDecimal percent;

    private PercentPaymentPeriodEntity periodEntity;

    private LocalDate percentPaymentDate;

    private boolean isCapitalization;

    private boolean isMonthly;

    private String description;

}
