package ru.sarmosov.deposit.deposit.percent.monthly;


import ru.sarmosov.bankstarter.enums.PercentPaymentPeriod;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MonthlyPercentDeposit extends AbstractMonthlyDeposit {

    public MonthlyPercentDeposit(BigDecimal balance, BigDecimal percent, LocalDate percentPaymentDate, PercentPaymentPeriod period, LocalDate startDate, LocalDate endDate, String token) {
        super(balance, percent, percentPaymentDate, period, startDate, endDate, token);
    }
}
