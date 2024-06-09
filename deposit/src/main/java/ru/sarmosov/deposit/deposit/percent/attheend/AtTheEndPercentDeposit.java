package ru.sarmosov.deposit.deposit.percent.attheend;

import ru.sarmosov.deposit.enums.PercentPaymentPeriod;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AtTheEndPercentDeposit extends AbstractAtTheEndDeposit {

    public AtTheEndPercentDeposit(BigDecimal balance, BigDecimal percent, LocalDate percentPaymentDate, PercentPaymentPeriod period, LocalDate startDate, LocalDate endDate, String token) {
        super(balance, percent, percentPaymentDate, period, startDate, endDate, token);
    }

}
