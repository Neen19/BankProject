package ru.sarmosov.deposit.service.deposit;

import ru.sarmosov.deposit.entity.DepositEntity;

import java.math.BigDecimal;

public interface DepositService {

    public void refreshBalance(DepositEntity deposit, BigDecimal balance);

    public DepositEntity addDeposit(DepositEntity deposit);

}
