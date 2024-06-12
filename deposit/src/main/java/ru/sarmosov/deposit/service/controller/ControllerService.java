package ru.sarmosov.deposit.service.controller;

import ru.sarmosov.bankstarter.dto.EmailConfirmDTO;
import ru.sarmosov.bankstarter.dto.RequestDTO;

public interface ControllerService {

    void sendRequest(RequestDTO requestDTO, String token);

    void emailConfirm(EmailConfirmDTO emailConfirmDTO);

}
