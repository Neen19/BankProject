package ru.sarmosov.account.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ru.sarmosov.account.dto.TotalDTO;
import ru.sarmosov.account.exception.InsufficientFundsException;
import ru.sarmosov.account.exception.UserNotFoundException;
import ru.sarmosov.account.service.BankAccountService;


@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class RestBankAccountController {

    private final BankAccountService bankAccountService;

    @PostMapping("/increase")
    public String increase(@RequestHeader("Authorization") String token, @RequestBody TotalDTO totalDTO) {
        System.out.println("increase");
        bankAccountService.increaseBalance(token, totalDTO);
        return "OK";
    }

    @PostMapping("/decrease")
    public void decrease(@RequestHeader("Authorization") String token, @RequestBody TotalDTO totalDTO) {
        System.out.println("lsllslslsl");
        bankAccountService.decreaseBalance(token, totalDTO);
    }

    @ExceptionHandler(UserNotFoundException.class)
    private ResponseEntity<String> handleException(UserNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InsufficientFundsException.class)
    private ResponseEntity<String> handleException(InsufficientFundsException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
