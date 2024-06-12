package ru.sarmosov.deposit.deposit.percent.monthly;

import ru.sarmosov.deposit.deposit.AbstractDeposit;
import ru.sarmosov.bankstarter.enums.PercentPaymentPeriod;
import ru.sarmosov.deposit.util.NetworkUtils;

import java.math.BigDecimal;
import java.time.LocalDate;

public abstract class AbstractMonthlyDeposit extends AbstractDeposit {

    public AbstractMonthlyDeposit(BigDecimal balance, BigDecimal percent, LocalDate percentPaymentDate, PercentPaymentPeriod period, LocalDate startDate, LocalDate endDate, String token) {
        super(balance, percent, percentPaymentDate, period, startDate, endDate);
        this.token = token;
    }

    protected final String token;

    @Override
    public BigDecimal payPercent() {
        LocalDate nowDate = LocalDate.now();
        BigDecimal resBalance = null;
        if (nowDate.equals(percentPaymentDate)) {
            BigDecimal percentSum = balance.multiply(percent.add(new BigDecimal(1)));
            LocalDate depositEndDate = startDate.plusMonths(period.getValue());
            if (LocalDate.now().equals(depositEndDate)) {
                endDate = LocalDate.now();
            }
            resBalance = NetworkUtils.increaseBalance(token, percentSum).getBalance();
            percentPaymentDate = percentPaymentDate.plusMonths(1);
        }
        balance = resBalance;
        return balance;
    }

}
