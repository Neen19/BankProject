package ru.sarmosov.deposit.service.request;


import ru.sarmosov.deposit.entity.RequestEntity;
import ru.sarmosov.bankstarter.enums.RequestStatus;

public interface RequestService {

    RequestEntity getById(Long id);

    RequestEntity updateRequestStatus(Long requestId, RequestStatus status);

    Iterable<RequestEntity> getRequests();

    RequestEntity addRequest(RequestEntity request);
}
