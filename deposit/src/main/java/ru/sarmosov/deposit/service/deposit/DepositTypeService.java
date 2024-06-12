package ru.sarmosov.deposit.service.deposit;

import ru.sarmosov.bankstarter.enums.DepositType;
import ru.sarmosov.deposit.entity.DepositTypeEntity;

public interface DepositTypeService {
    DepositTypeEntity getPersistenceEntity(DepositType depositType);
}
