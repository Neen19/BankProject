package ru.sarmosov.deposit.service.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sarmosov.bankstarter.annotation.Logging;
import ru.sarmosov.deposit.entity.RequestEntity;
import ru.sarmosov.deposit.entity.RequestStatusEntity;
import ru.sarmosov.bankstarter.enums.RequestStatus;
import ru.sarmosov.deposit.repository.RequestRepository;
import ru.sarmosov.deposit.repository.RequestStatusRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final RequestStatusRepository requestStatusRepository;

    @Logging
    @Transactional
    public RequestEntity addRequest(RequestEntity request) {
        return requestRepository.save(request);
    }

    @Logging
    @Transactional
    public int getIdByStatus(RequestStatus status) {
        return requestStatusRepository.findByStatus(status).orElseThrow().getId();
    }

    @Logging
    @Transactional
    public RequestEntity getById(Long id) {
        return requestRepository.findById(id).orElseThrow();
    }

    @Logging
    @Transactional
    public RequestEntity saveRequest(RequestEntity request) {
        return requestRepository.save(request);
    }

    @Logging
    @Override
    @Transactional
    public RequestEntity updateRequestStatus(Long requestId, RequestStatus status) {
        Integer requestStatusId = getIdByStatus(status);
        RequestEntity requestEntity = requestRepository.findById(requestId).orElseThrow();
        RequestStatusEntity requestStatusEntity = requestStatusRepository.findById(requestStatusId).orElseThrow();
        requestEntity.setStatus(requestStatusEntity);
        return requestRepository.save(requestEntity);
    }

    @Logging
    @Override
    @Transactional
    public RequestEntity updateRequestDescription(Long requestId, String description) {
        RequestEntity requestEntity = requestRepository.findById(requestId).orElseThrow();
        requestEntity.setDescription(description);
        return requestRepository.save(requestEntity);
    }

    @Logging
    @Transactional
    public Iterable<RequestEntity> getRequests() {
        return requestRepository.findAll();
    }

    @Logging
    @Transactional
    public List<RequestEntity> getCustomerRequests(Long customerId) {
        return requestRepository.findAllByCustomerId(customerId);
    }
}
