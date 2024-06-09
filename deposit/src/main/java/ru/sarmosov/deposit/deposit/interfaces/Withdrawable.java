package ru.sarmosov.deposit.deposit.interfaces;

import ru.sarmosov.bankstarter.exception.InsufficientFundsException;

import java.math.BigDecimal;

public interface Withdrawable {

    BigDecimal withdraw(BigDecimal amount) throws InsufficientFundsException;

}
