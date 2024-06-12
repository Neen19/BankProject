package ru.sarmosov.deposit.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import ru.sarmosov.bankstarter.dto.BalanceDTO;
import ru.sarmosov.bankstarter.dto.EmailConfirmDTO;
import ru.sarmosov.bankstarter.dto.RequestDTO;
import ru.sarmosov.bankstarter.dto.TotalDTO;
import ru.sarmosov.deposit.service.controller.ControllerService;

@RestController
@RequiredArgsConstructor
public class RestDepositController {

    private final ControllerService controllerService;

    @PostMapping("/increase")
    public String increase(@RequestHeader("Authorization") String token, @RequestBody RequestDTO request) {
        controllerService.sendRequest(request, token);
        return "Request recieved";
    }

    @PostMapping("/confirm")
    public String confirm(@RequestBody EmailConfirmDTO confirmDTO) {
        controllerService.emailConfirm(confirmDTO);
        return "Code confirmed";
    }



}
