package ru.sarmosov.deposit.deposit.percent.monthly;

import ru.sarmosov.deposit.deposit.interfaces.Depositable;
import ru.sarmosov.deposit.deposit.interfaces.Withdrawable;
import ru.sarmosov.deposit.enums.PercentPaymentPeriod;
import ru.sarmosov.deposit.util.NetworkUtils;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MonthlyDepositableWithdrawablePercentDeposit extends AbstractMonthlyDeposit implements Depositable, Withdrawable {

    public MonthlyDepositableWithdrawablePercentDeposit(BigDecimal balance, BigDecimal percent, LocalDate percentPaymentDate, PercentPaymentPeriod period, LocalDate startDate, LocalDate endDate, String token) {
        super(balance, percent, percentPaymentDate, period, startDate, endDate, token);
    }

    @Override
    public BigDecimal deposit(BigDecimal amount) {
        return NetworkUtils.increaseBalance(token, amount).getBalance();
    }

    @Override
    public BigDecimal withdraw(BigDecimal amount) {
        return NetworkUtils.decreaseBalance(token, amount).getBalance();
    }

}
