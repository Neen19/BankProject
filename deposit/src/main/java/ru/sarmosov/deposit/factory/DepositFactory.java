package ru.sarmosov.deposit.factory;

import ru.sarmosov.deposit.deposit.AbstractDeposit;
import ru.sarmosov.deposit.entity.DepositEntity;

import java.util.List;

public interface DepositFactory {
    public List<AbstractDeposit> getDeposits(List<DepositEntity> deposits);
}
