package ru.sarmosov.deposit.service.deposit;

import ru.sarmosov.bankstarter.enums.RequestStatus;
import ru.sarmosov.deposit.entity.RequestStatusEntity;

public interface RequestStatusService {

    RequestStatusEntity getPersistenceEntity(RequestStatus status);

}
