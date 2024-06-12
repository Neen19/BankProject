package ru.sarmosov.customerservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import ru.sarmosov.bankstarter.annotation.Logging;
import ru.sarmosov.bankstarter.dto.AuthDTO;
import ru.sarmosov.bankstarter.dto.CustomerDTO;
import ru.sarmosov.bankstarter.dto.ErrorResponseDTO;
import ru.sarmosov.bankstarter.dto.TokenResponseDTO;
import ru.sarmosov.customerservice.service.JWTService;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class RestAuthController {

    private final JWTService jwtService;

    @Logging(value = "Вызов login эндпоинта")
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody @Valid AuthDTO authDTO) throws BadCredentialsException {
        return new ResponseEntity<>(jwtService.getCustomerToken(authDTO), HttpStatus.OK);
    }


    @Logging(value = "Получена ошибка, пользователь не найден")
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponseDTO> handleException(BadCredentialsException e) {
        return new ResponseEntity<>(new ErrorResponseDTO("User not found"), HttpStatus.BAD_REQUEST);
    }



}
