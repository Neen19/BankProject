package ru.sarmosov.deposit.service.deposit;

import ru.sarmosov.bankstarter.enums.RequestStatus;
import ru.sarmosov.deposit.entity.DepositEntity;
import ru.sarmosov.deposit.entity.RequestStatusEntity;

import java.util.List;

public interface RequestStatusService {

    RequestStatusEntity getPersistenceEntity(RequestStatus status);


}
