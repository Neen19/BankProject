package ru.sarmosov.customerservice.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import ru.sarmosov.customerservice.dto.AuthDTO;
import ru.sarmosov.bankstarter.dto.CustomerDTO;
import ru.sarmosov.customerservice.dto.TokenResponse;
import ru.sarmosov.customerservice.service.JWTService;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class RestAuthController {

    private final JWTService jwtService;
    private final ModelMapper modelMapper;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody AuthDTO authDTO) throws BadCredentialsException {
        return new ResponseEntity<>(jwtService.getCustomerToken(authDTO), HttpStatus.OK);
    }

    @GetMapping("/customer")
    public ResponseEntity<CustomerDTO> decode(@RequestHeader("Authorization") String token) throws BadCredentialsException {
        CustomerDTO dto = modelMapper.map(jwtService.getCustomerByToken(token), CustomerDTO.class);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @ExceptionHandler(BadCredentialsException.class)
    private ResponseEntity<String> handleException(BadCredentialsException e) {
        return new ResponseEntity<>("User not found", HttpStatus.UNAUTHORIZED);
    }



}
