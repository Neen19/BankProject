package ru.sarmosov.deposit.deposit.interfaces;

import java.math.BigDecimal;

public interface Depositable {

    BigDecimal deposit(BigDecimal amount);

}
