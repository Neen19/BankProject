package ru.sarmosov.deposit.service.deposit;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sarmosov.bankstarter.annotation.Logging;
import ru.sarmosov.bankstarter.enums.RequestStatus;
import ru.sarmosov.deposit.entity.DepositEntity;
import ru.sarmosov.deposit.entity.RequestStatusEntity;
import ru.sarmosov.deposit.repository.RequestStatusRepository;
import ru.sarmosov.deposit.service.request.RequestService;

import java.util.List;


@Service
@RequiredArgsConstructor
public class RequestStatusServiceImpl implements RequestStatusService {

    private final RequestStatusRepository repository;

    @Logging
    public RequestStatusEntity getPersistenceEntity(RequestStatus status) {
        return repository.findByStatus(status).orElseThrow();
    }

}