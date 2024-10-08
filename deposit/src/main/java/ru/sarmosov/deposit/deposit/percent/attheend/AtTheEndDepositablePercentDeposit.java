package ru.sarmosov.deposit.deposit.percent.attheend;


import jakarta.validation.Valid;
import ru.sarmosov.bankstarter.annotation.Logging;
import ru.sarmosov.deposit.deposit.interfaces.Depositable;
import ru.sarmosov.bankstarter.enums.PercentPaymentPeriod;
import ru.sarmosov.deposit.util.NetworkUtils;

import java.math.BigDecimal;
import java.time.LocalDate;


public class AtTheEndDepositablePercentDeposit extends AbstractAtTheEndDeposit implements Depositable {

    public AtTheEndDepositablePercentDeposit(BigDecimal balance, BigDecimal percent, LocalDate percentPaymentDate, PercentPaymentPeriod period, LocalDate startDate, LocalDate endDate, String token) {
        super(balance, percent, percentPaymentDate, period, startDate, endDate, token);
    }

    @Logging(value = "Снятие денег со вклада")
    @Override
    public BigDecimal deposit(BigDecimal amount) {
        return NetworkUtils.increaseBalance(token, amount).getBalance();
    }



}
