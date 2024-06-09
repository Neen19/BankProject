package ru.sarmosov.deposit.deposit.capitalization;

import ru.sarmosov.deposit.deposit.AbstractDeposit;
import ru.sarmosov.deposit.enums.PercentPaymentPeriod;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

abstract class AbstractCapitalizationDeposit extends AbstractDeposit {

    @Override
    public BigDecimal payPercent() {
        balance = balance.multiply(percent.add(new BigDecimal(1)));
        LocalDate depositEndDate = startDate.plusMonths(period.getValue());
        if (LocalDate.now().equals(depositEndDate)) {
            endDate = LocalDate.now();
        }
        percentPaymentDate = percentPaymentDate.plusMonths(1);
        return balance;
    }

    public AbstractCapitalizationDeposit(BigDecimal balance, BigDecimal percent, LocalDate percentPaymentDate, PercentPaymentPeriod period, LocalDate startDate, LocalDate endDate) {
        super(balance, percent, percentPaymentDate, period, startDate, endDate);
    }
}
