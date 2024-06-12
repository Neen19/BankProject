package ru.sarmosov.deposit.deposit.capitalization;

import ru.sarmosov.deposit.deposit.interfaces.Depositable;
import ru.sarmosov.bankstarter.enums.PercentPaymentPeriod;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DepositableCapitalizationDeposit extends AbstractCapitalizationDeposit implements Depositable {

    @Override
    public BigDecimal deposit(BigDecimal amount) {
        balance = balance.add(amount);
        return balance;
    }

    public DepositableCapitalizationDeposit(BigDecimal balance, BigDecimal percent, LocalDate percentPaymentDate, PercentPaymentPeriod period, LocalDate startDate, LocalDate endDate) {
        super(balance, percent, percentPaymentDate, period, startDate, endDate);
    }
}
