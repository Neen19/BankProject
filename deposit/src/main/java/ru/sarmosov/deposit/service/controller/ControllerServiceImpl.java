package ru.sarmosov.deposit.service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sarmosov.bankstarter.annotation.Logging;
import ru.sarmosov.bankstarter.dto.DepositTotalDTO;
import ru.sarmosov.bankstarter.dto.EmailConfirmDTO;
import ru.sarmosov.bankstarter.dto.IdDTO;
import ru.sarmosov.bankstarter.dto.RequestDTO;
import ru.sarmosov.bankstarter.util.JWTUtil;
import ru.sarmosov.deposit.entity.DepositEntity;
import ru.sarmosov.deposit.entity.RequestEntity;
import ru.sarmosov.deposit.exception.DepositNotFountException;
import ru.sarmosov.deposit.factory.RequestFactory;
import ru.sarmosov.deposit.service.deposit.DepositService;
import ru.sarmosov.deposit.handler.RequestHandler;
import ru.sarmosov.deposit.service.request.RequestService;

import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class ControllerServiceImpl implements ControllerService {

    private final RequestFactory requestFactory;
    private final RequestHandler handler;
    private final JWTUtil jwtUtil;
    private final ConcurrentHashMap<Long, Integer> emailCodeMap;
    private final RequestService requestService;
    private final DepositService depositService;

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
    public DepositEntity shutDownDeposit(IdDTO idDTO) {
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

}
