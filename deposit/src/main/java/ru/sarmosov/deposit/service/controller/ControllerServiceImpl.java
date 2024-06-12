package ru.sarmosov.deposit.service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sarmosov.bankstarter.dto.EmailConfirmDTO;
import ru.sarmosov.bankstarter.dto.RequestDTO;
import ru.sarmosov.bankstarter.util.JWTUtil;
import ru.sarmosov.deposit.entity.RequestEntity;
import ru.sarmosov.deposit.factory.DepositFactory;
import ru.sarmosov.deposit.factory.RequestFactory;
import ru.sarmosov.deposit.service.handler.RequestHandler;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class ControllerServiceImpl implements ControllerService {

    private final RequestFactory requestFactory ;
    private final RequestHandler handler;
    private final JWTUtil jwtUtil;
    private final ConcurrentHashMap<Long, Integer> emailCodeMap;

    @Override
    public void emailConfirm(EmailConfirmDTO emailConfirmDTO) {
        emailCodeMap.put(emailConfirmDTO.getRequestId(), emailConfirmDTO.getCode());
        System.out.println("added with key" + emailConfirmDTO.getRequestId() + emailCodeMap.get(emailConfirmDTO.getRequestId()));
    }

    @Override
    public void sendRequest(RequestDTO requestDTO, String token) {
        token = jwtUtil.trimToken(token);
        RequestEntity entity = requestFactory.convertRequestDTOToEntity(requestDTO, token);
        System.out.println("added request");
        handler.addRequest(entity);
    }

}
