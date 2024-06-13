package ru.sarmosov.deposit.deposit;

import lombok.*;
import ru.sarmosov.bankstarter.annotation.Logging;
import ru.sarmosov.bankstarter.enums.PercentPaymentPeriod;
import ru.sarmosov.deposit.exception.EndedDepositException;
import ru.sarmosov.deposit.util.NetworkUtils;


import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractDeposit {

    public abstract BigDecimal payPercent();

    @Logging(value = "Закрытие вклада")
    public void shutDown() {
        LocalDate now = LocalDate.now();
        if (now.isAfter(endDate)) {
            throw new EndedDepositException("deposit already ended");
        }
        NetworkUtils.increaseBalance(token, balance);
    }

    protected BigDecimal balance;

    protected BigDecimal percent;

    protected LocalDate percentPaymentDate;

    protected PercentPaymentPeriod period;

    protected LocalDate startDate;

    protected LocalDate endDate;

    protected String token;

    @Override
    public String toString() {
        return "AbstractDeposit{" +
                "class=" + this.getClass() +
                "balance=" + balance +
                ", percent=" + percent +
                ", percentPaymentDate=" + percentPaymentDate +
                ", period=" + period +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
