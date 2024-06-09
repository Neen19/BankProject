package ru.sarmosov.deposit.deposit.percent.attheend;


import ru.sarmosov.deposit.deposit.interfaces.Depositable;
import ru.sarmosov.deposit.enums.PercentPaymentPeriod;
import ru.sarmosov.deposit.util.NetworkUtils;

import java.math.BigDecimal;
import java.time.LocalDate;


public class AtTheEndDepositablePercentDeposit extends AbstractAtTheEndDeposit implements Depositable {

    public AtTheEndDepositablePercentDeposit(BigDecimal balance, BigDecimal percent, LocalDate percentPaymentDate, PercentPaymentPeriod period, LocalDate startDate, LocalDate endDate, String token) {
        super(balance, percent, percentPaymentDate, period, startDate, endDate, token);
    }

    @Override
    public BigDecimal deposit(BigDecimal amount) {
        return NetworkUtils.increaseBalance(token, amount).getBalance();
    }



}
