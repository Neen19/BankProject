package ru.sarmosov.deposit.deposit.capitalization;

import ru.sarmosov.bankstarter.annotation.Logging;
import ru.sarmosov.bankstarter.exception.InsufficientFundsException;
import ru.sarmosov.deposit.deposit.interfaces.Depositable;
import ru.sarmosov.deposit.deposit.interfaces.Withdrawable;
import ru.sarmosov.bankstarter.enums.PercentPaymentPeriod;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DepositableWithdrawableCapitalizationDeposit
        extends AbstractCapitalizationDeposit implements Depositable, Withdrawable {

    @Logging(value = "Снятие денег со вклада")
    @Override
    public BigDecimal deposit(BigDecimal amount) {
        balance = balance.add(amount);
        return balance;
    }

    @Logging(value = "Пополнение вклада")
    @Override
    public BigDecimal withdraw(BigDecimal amount) throws InsufficientFundsException {
        BigDecimal result = balance.subtract(amount);
        if (result.compareTo(BigDecimal.ZERO) < 0) {
            throw new InsufficientFundsException();
        } else balance = result;
        return balance;
    }

    public DepositableWithdrawableCapitalizationDeposit(BigDecimal balance, BigDecimal percent, LocalDate percentPaymentDate, PercentPaymentPeriod period, LocalDate startDate, LocalDate endDate) {
        super(balance, percent, percentPaymentDate, period, startDate, endDate);
    }

}
