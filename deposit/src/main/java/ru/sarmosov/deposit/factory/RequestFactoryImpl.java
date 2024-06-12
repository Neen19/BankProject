package ru.sarmosov.deposit.factory;

import lombok.RequiredArgsConstructor;
import ru.sarmosov.bankstarter.dto.CustomerDTO;
import ru.sarmosov.bankstarter.dto.RequestDTO;
import ru.sarmosov.bankstarter.enums.RequestStatus;
import ru.sarmosov.bankstarter.util.JWTUtil;
import ru.sarmosov.deposit.entity.RequestEntity;
import ru.sarmosov.deposit.entity.RequestStatusEntity;
import ru.sarmosov.deposit.repository.RequestStatusRepository;
import ru.sarmosov.deposit.service.request.RequestService;

@RequiredArgsConstructor
public class RequestFactoryImpl implements RequestFactory {

    private final JWTUtil jwtUtil;
    private final RequestStatusRepository statusRepository;
    private final RequestService requestService;

    @Override
    public RequestEntity convertRequestDTOToEntity(RequestDTO requestDTO, String token) {
        CustomerDTO customerDTO = jwtUtil.verifyTokenAndRetrievePhoneNumber(token);
        RequestStatusEntity status = statusRepository.findByStatus(RequestStatus.PENDING_CONFIRMATION).orElseThrow();
        RequestEntity requestEntity= new RequestEntity(
                requestDTO,
                customerDTO,
                token,
                status
        );
        System.out.println();
        return requestService.addRequest(requestEntity);
    }

}
