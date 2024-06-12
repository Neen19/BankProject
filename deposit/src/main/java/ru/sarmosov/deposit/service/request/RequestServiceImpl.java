package ru.sarmosov.deposit.service.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sarmosov.deposit.entity.RequestEntity;
import ru.sarmosov.deposit.entity.RequestStatusEntity;
import ru.sarmosov.bankstarter.enums.RequestStatus;
import ru.sarmosov.deposit.repository.RequestRepository;
import ru.sarmosov.deposit.repository.RequestStatusRepository;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final RequestStatusRepository requestStatusRepository;

    @Transactional
    public RequestEntity addRequest(RequestEntity request) {
        return requestRepository.save(request);
    }

    @Transactional
    public int getIdByStatus(RequestStatus status) {
        return requestStatusRepository.findByStatus(status).orElseThrow().getId();
    }

    @Transactional
    public RequestEntity getById(Long id) {
        return requestRepository.findById(id).orElseThrow();
    }

    @Transactional
    public RequestEntity saveRequest(RequestEntity request) {
        return requestRepository.save(request);
    }

    @Override
    @Transactional
    public RequestEntity updateRequestStatus(Long requestId, RequestStatus status) {
        Integer requestStatusId = getIdByStatus(status);
        RequestEntity requestEntity = requestRepository.findById(requestId).orElseThrow();
        RequestStatusEntity requestStatusEntity = requestStatusRepository.findById(requestStatusId).orElseThrow();
        requestEntity.setStatus(requestStatusEntity);
        return requestRepository.save(requestEntity);
    }

    @Transactional
    public Iterable<RequestEntity> getRequests() {
        return requestRepository.findAll();
    }
}
