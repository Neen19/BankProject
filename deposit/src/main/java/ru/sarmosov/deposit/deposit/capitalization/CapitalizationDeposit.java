package ru.sarmosov.deposit.deposit.capitalization;

import ru.sarmosov.bankstarter.enums.PercentPaymentPeriod;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CapitalizationDeposit extends AbstractCapitalizationDeposit  {

    public CapitalizationDeposit(BigDecimal balance, BigDecimal percent, LocalDate percentPaymentDate, PercentPaymentPeriod period, LocalDate startDate, LocalDate endDate) {
        super(balance, percent, percentPaymentDate, period, startDate, endDate);
    }


}
