package ru.sarmosov.deposit.service.controller;

import ru.sarmosov.bankstarter.dto.DepositTotalDTO;
import ru.sarmosov.bankstarter.dto.EmailConfirmDTO;
import ru.sarmosov.bankstarter.dto.IdDTO;
import ru.sarmosov.bankstarter.dto.RequestDTO;
import ru.sarmosov.deposit.dto.DepositDTO;
import ru.sarmosov.deposit.dto.RequestResponseDTO;
import ru.sarmosov.deposit.entity.DepositEntity;
import ru.sarmosov.deposit.exception.DepositNotFountException;

import java.util.List;

public interface ControllerService {

    void sendRequest(RequestDTO requestDTO, String token);

    void emailConfirm(EmailConfirmDTO emailConfirmDTO);

    DepositEntity shutDownDeposit(IdDTO idDTO, String token);

    DepositTotalDTO increaseDepositBalance(DepositTotalDTO depositTotalDTO, String token) throws DepositNotFountException;

    DepositTotalDTO decreaseDepositBalance(DepositTotalDTO depositTotalDTO, String token) throws DepositNotFountException;

    List<DepositDTO> getDeposits(String token);

    List<RequestResponseDTO> getRequests(String token);
}
