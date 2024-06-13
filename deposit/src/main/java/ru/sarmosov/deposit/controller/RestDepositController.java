package ru.sarmosov.deposit.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sarmosov.bankstarter.annotation.Logging;
import ru.sarmosov.bankstarter.dto.*;
import ru.sarmosov.deposit.dto.DepositDTO;
import ru.sarmosov.deposit.dto.RequestResponseDTO;
import ru.sarmosov.deposit.exception.ConstructorException;
import ru.sarmosov.deposit.exception.DepositNotFountException;
import ru.sarmosov.deposit.exception.UndefinedException;
import ru.sarmosov.deposit.service.controller.ControllerService;

import java.util.List;


@RestController
@RequestMapping("/api/deposit")
@RequiredArgsConstructor
public class RestDepositController {

    private final ControllerService controllerService;

    @Logging(value = "Вызов request эндпоинта")
    @PostMapping("/request")
    public String increase(@RequestHeader("Authorization") String token, @Valid @RequestBody RequestDTO request) {
        controllerService.sendRequest(request, token);
        return "Request received";
    }

    @Logging(value = "Вызов confirm эндпоинта")
    @PostMapping("/confirm")
    public String confirm(@Valid @RequestBody EmailConfirmDTO confirmDTO) {
        controllerService.emailConfirm(confirmDTO);
        return "Code confirmed";
    }


    @Logging(value = "Вызов shutdown эндпоинта")
    @PostMapping("/shutdown")
    public String shutDown(@RequestHeader("Authorization") String token,
                           @Valid @RequestBody IdDTO dto) {
        controllerService.shutDownDeposit(dto, token);
        return "Deposit shut down";
    }


    @Logging(value = "Вызов increase эндпоинта")
    @PostMapping("/increase")
    public DepositTotalDTO increase(
            @RequestHeader("Authorization") String token,
            @Valid @RequestBody DepositTotalDTO depositTotalDTO) throws DepositNotFountException {
        return controllerService.increaseDepositBalance(depositTotalDTO, token);
    }


    @Logging(value = "Вызов decrease эндпоинта")
    @PostMapping("/decrease")
    public DepositTotalDTO decrease(
            @RequestHeader("Authorization") String token,
            @Valid @RequestBody DepositTotalDTO depositTotalDTO) throws DepositNotFountException {
        return controllerService.decreaseDepositBalance(depositTotalDTO, token);
    }

    @Logging(value = "Вызов requests эндпоинта")
    @GetMapping("/requests")
    public List<RequestResponseDTO> requests(
            @RequestHeader("Authorization") String token) {
        return controllerService.getRequests(token);
    }


    @Logging(value = "Вызов deposits эндпоинта")
    @GetMapping("/deposits")
    public List<DepositDTO> deposits(
            @RequestHeader("Authorization") String token) {
        return controllerService.getDeposits(token);
    }



    @ExceptionHandler(DepositNotFountException.class)
    public ResponseEntity<ErrorResponseDTO> handleException(DepositNotFountException e) {
        return new ResponseEntity<>(new ErrorResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(UndefinedException.class)
    public ResponseEntity<ErrorResponseDTO> handleException(UndefinedException e) {
        return new ResponseEntity<>(new ErrorResponseDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConstructorException.class)
    public ResponseEntity<ErrorResponseDTO> handleException(ConstructorException e) {
        return new ResponseEntity<>(new ErrorResponseDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
