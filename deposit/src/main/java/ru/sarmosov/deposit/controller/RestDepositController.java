package ru.sarmosov.deposit.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import ru.sarmosov.bankstarter.annotation.Logging;
import ru.sarmosov.bankstarter.dto.*;
import ru.sarmosov.deposit.exception.DepositNotFountException;
import ru.sarmosov.deposit.service.controller.ControllerService;

import javax.crypto.spec.PSource;

@RestController
@RequiredArgsConstructor
public class RestDepositController {

    private final ControllerService controllerService;

    @PostMapping("/request")
    public String increase(@RequestHeader("Authorization") String token, @RequestBody RequestDTO request) {
        controllerService.sendRequest(request, token);
        return "Request recieved";
    }

    @PostMapping("/confirm")
    public String confirm(@RequestHeader("Authorization") String token, @RequestBody EmailConfirmDTO confirmDTO) {
        controllerService.emailConfirm(confirmDTO);
        return "Code confirmed";
    }


    @PostMapping("/shutdown")
    public String shutDown(@RequestHeader("Authorization") String token, @RequestBody IdDTO dto) {
        controllerService.shutDownDeposit(dto);
        return "Deposit shuted down";
    }


    @PostMapping("/increase")
    @Logging(entering = true)
    public DepositTotalDTO increase(
            @RequestHeader("Authorization") String token,
            @RequestBody DepositTotalDTO depositTotalDTO) throws DepositNotFountException {
        return controllerService.increaseDepositBalance(depositTotalDTO, token);
    }


    @PostMapping("/decrease")
    public DepositTotalDTO decrease(
            @RequestHeader("Authorization") String token,
            @RequestBody DepositTotalDTO depositTotalDTO) throws DepositNotFountException {
        return controllerService.decreaseDepositBalance(depositTotalDTO, token);
    }


}
