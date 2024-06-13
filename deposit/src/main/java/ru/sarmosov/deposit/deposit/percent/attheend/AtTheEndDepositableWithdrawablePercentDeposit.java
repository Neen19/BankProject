package ru.sarmosov.deposit.deposit.percent.attheend;

import ru.sarmosov.bankstarter.annotation.Logging;
import ru.sarmosov.deposit.deposit.interfaces.Depositable;
import ru.sarmosov.deposit.deposit.interfaces.Withdrawable;
import ru.sarmosov.bankstarter.enums.PercentPaymentPeriod;
import ru.sarmosov.deposit.util.NetworkUtils;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AtTheEndDepositableWithdrawablePercentDeposit extends AbstractAtTheEndDeposit implements Depositable, Withdrawable {

    public AtTheEndDepositableWithdrawablePercentDeposit(BigDecimal balance, BigDecimal percent, LocalDate percentPaymentDate, PercentPaymentPeriod period, LocalDate startDate, LocalDate endDate, String token) {
        super(balance, percent, percentPaymentDate, period, startDate, endDate, token);
    }

    @Logging(value = "Пополнение вклада")
    @Override
    public BigDecimal deposit(BigDecimal amount) {
        return NetworkUtils.increaseBalance(token, amount).getBalance();
    }

    @Logging(value = "Снятие денег со вклада")
    @Override
    public BigDecimal withdraw(BigDecimal amount) {
        return NetworkUtils.decreaseBalance(token, amount).getBalance();
    }

}