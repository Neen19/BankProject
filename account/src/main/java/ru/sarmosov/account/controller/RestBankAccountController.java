package ru.sarmosov.account.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ru.sarmosov.bankstarter.dto.BalanceDTO;
import ru.sarmosov.bankstarter.dto.TotalDTO;
import ru.sarmosov.account.exception.InsufficientFundsException;
import ru.sarmosov.bankstarter.exception.UserNotFoundException;
import ru.sarmosov.account.service.BankAccountService;


@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class RestBankAccountController {

    private final BankAccountService bankAccountService;

    @PostMapping("/increase")
    public BalanceDTO increase(@RequestHeader("Authorization") String token, @RequestBody TotalDTO totalDTO) {
        return bankAccountService.increaseBalance(token, totalDTO);
    }

    @PostMapping("/decrease")
    public BalanceDTO decrease(@RequestHeader("Authorization") String token, @RequestBody TotalDTO totalDTO) {
        return bankAccountService.decreaseBalance(token, totalDTO);
    }

    @GetMapping("/balance")
    public BalanceDTO balance(@RequestHeader("Authorization") String token) {
        return bankAccountService.getBalance(token);
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
