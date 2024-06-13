package ru.sarmosov.account.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.sarmosov.bankstarter.annotation.Logging;
import ru.sarmosov.bankstarter.dto.BalanceDTO;
import ru.sarmosov.bankstarter.dto.ErrorResponseDTO;
import ru.sarmosov.bankstarter.dto.TotalDTO;
import ru.sarmosov.account.exception.InsufficientFundsException;
import ru.sarmosov.bankstarter.exception.TokenVerificationException;
import ru.sarmosov.bankstarter.exception.UserNotFoundException;
import ru.sarmosov.account.service.BankAccountService;


@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class RestBankAccountController {

    private final BankAccountService bankAccountService;

    @Logging(value = "Вызов эндпоинта increase")
    @PostMapping("/increase")
    public BalanceDTO increase(@RequestHeader("Authorization") String token, @RequestBody @Valid TotalDTO totalDTO) {
        return bankAccountService.increaseBalance(token, totalDTO);
    }

    @Logging(value = "Вызов эндпоинта decrease")
    @PostMapping("/decrease")
    public BalanceDTO decrease(@RequestHeader("Authorization") String token, @RequestBody @Valid TotalDTO totalDTO) {

        return bankAccountService.decreaseBalance(token, totalDTO);
    }

    @Logging(value = "Вызов эндпоинта balance")
    @GetMapping("/balance")
    public BalanceDTO balance(@RequestHeader("Authorization") String token) {
        return bankAccountService.getBalance(token);
    }

    @Logging(value = "Пользователь не найден, отправка ошибки")
    @ExceptionHandler(UserNotFoundException.class)
    private ResponseEntity<String> handleException(UserNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
    }

    @Logging(value = "У пользователя не хватает денег на вывод средств")
    @ExceptionHandler(InsufficientFundsException.class)
    private ResponseEntity<ErrorResponseDTO> handleException(InsufficientFundsException e) {
        return new ResponseEntity<>(new ErrorResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
    }


}
