package ru.sarmosov.deposit.factory;

import ru.sarmosov.bankstarter.dto.RequestDTO;
import ru.sarmosov.deposit.deposit.AbstractDeposit;
import ru.sarmosov.deposit.entity.DepositEntity;
import ru.sarmosov.deposit.entity.RequestEntity;

import java.util.List;

public interface DepositFactory {

    DepositEntity convertRequestEntityToDepositEntity(RequestEntity request, String token);

    List<AbstractDeposit> getDeposits(Iterable<DepositEntity> deposits);

    AbstractDeposit convertDepositEntityToAbstractDeposit(DepositEntity depositEntity);
}
