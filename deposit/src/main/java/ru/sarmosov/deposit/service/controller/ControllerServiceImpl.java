package ru.sarmosov.deposit.service.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.sarmosov.bankstarter.annotation.Logging;
import ru.sarmosov.bankstarter.dto.DepositTotalDTO;
import ru.sarmosov.bankstarter.dto.EmailConfirmDTO;
import ru.sarmosov.bankstarter.dto.IdDTO;
import ru.sarmosov.bankstarter.dto.RequestDTO;
import ru.sarmosov.bankstarter.util.JWTUtil;
import ru.sarmosov.deposit.dto.DepositDTO;
import ru.sarmosov.deposit.dto.RequestResponseDTO;
import ru.sarmosov.deposit.entity.DepositEntity;
import ru.sarmosov.deposit.entity.RequestEntity;
import ru.sarmosov.deposit.exception.DepositNotFountException;
import ru.sarmosov.deposit.factory.RequestFactory;
import ru.sarmosov.deposit.service.deposit.DepositService;
import ru.sarmosov.deposit.handler.RequestHandler;
import ru.sarmosov.deposit.service.request.RequestService;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ControllerServiceImpl implements ControllerService {

    private final RequestFactory requestFactory;
    private final RequestHandler handler;
    private final JWTUtil jwtUtil;
    private final ConcurrentHashMap<Long, Integer> emailCodeMap;
    private final RequestService requestService;
    private final DepositService depositService;
    private final ModelMapper modelMapper;

    @Override
    public void emailConfirm(EmailConfirmDTO emailConfirmDTO) {
        emailCodeMap.put(emailConfirmDTO.getRequestId(), emailConfirmDTO.getCode());
        System.out.println("added with key" + emailConfirmDTO.getRequestId() + emailCodeMap.get(emailConfirmDTO.getRequestId()));
    }

    @Override
    public void sendRequest(RequestDTO requestDTO, String token) {
        token = jwtUtil.trimToken(token);
        RequestEntity entity = requestFactory.convertRequestDTOToEntity(requestDTO, token);
        requestService.addRequest(entity);
        handler.addRequest(entity);
    }

    @Override
    public DepositEntity shutDownDeposit(IdDTO idDTO, String token) {
        jwtUtil.verifyTokenAndRetrievePhoneNumber(token);
        return depositService.shutDownDeposit(idDTO.getId());
    }

    @Override
    public DepositTotalDTO increaseDepositBalance(DepositTotalDTO depositTotalDTO, String token) throws DepositNotFountException {
        return depositService.increaseBalance(depositTotalDTO, token);
    }

    @Override
    public DepositTotalDTO decreaseDepositBalance(DepositTotalDTO depositTotalDTO, String token) throws DepositNotFountException {
        return depositService.decreaseBalance(depositTotalDTO, token);
    }

    @Override
    public List<DepositDTO> getDeposits(String token) {
        return depositService.getCustomerDeposits(token).stream()
                .map(it->modelMapper.map(it, DepositDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<RequestResponseDTO> getRequests(String token) {
        token = jwtUtil.trimToken(token);
        Long customerId = jwtUtil.verifyTokenAndRetrievePhoneNumber(token).getId();
        return requestService.getCustomerRequests(customerId).stream()
                .map(it->modelMapper.map(it, RequestResponseDTO.class))
                .collect(Collectors.toList());
    }
}
