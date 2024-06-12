package ru.sarmosov.deposit.deposit.percent.attheend;

import ru.sarmosov.deposit.deposit.AbstractDeposit;
import ru.sarmosov.bankstarter.enums.PercentPaymentPeriod;
import ru.sarmosov.deposit.util.NetworkUtils;

import java.math.BigDecimal;
import java.time.LocalDate;

public abstract class AbstractAtTheEndDeposit extends AbstractDeposit {

    public AbstractAtTheEndDeposit(BigDecimal balance, BigDecimal percent, LocalDate percentPaymentDate, PercentPaymentPeriod period, LocalDate startDate, LocalDate endDate, String token) {
        super(balance, percent, percentPaymentDate, period, startDate, endDate);
        this.token = token;
    }

    protected final String token;

    @Override
    public BigDecimal payPercent() {

        BigDecimal resBalance = balance;
        if (LocalDate.now().equals(endDate)) {
            BigDecimal percentSum = balance.multiply(percent.add(new BigDecimal(1)));
            resBalance = NetworkUtils.increaseBalance(token, percentSum).getBalance();
            endDate = LocalDate.now();
        }
        return resBalance;
    }
}
