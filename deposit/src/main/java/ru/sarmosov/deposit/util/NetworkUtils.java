package ru.sarmosov.deposit.util;


import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import ru.sarmosov.bankstarter.dto.BalanceDTO;
import ru.sarmosov.bankstarter.dto.TotalDTO;
import ru.sarmosov.bankstarter.exception.UserNotFoundException;

import java.math.BigDecimal;

public class NetworkUtils {
    private static final RestTemplate restTemplate = new RestTemplate();

    private static final String BASE_URL = "http://localhost:5252/api/account";

    private static final String AUTHORIZATION_HEADER = "Authorization";


    private static BalanceDTO httpRequestToAccount(String token, BigDecimal amount, String endpoint) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(AUTHORIZATION_HEADER, token);
        TotalDTO totalDTO = new TotalDTO(amount);
        HttpEntity<TotalDTO> entity = new HttpEntity<>(totalDTO, headers);
        String url = BASE_URL + endpoint;

        ResponseEntity<BalanceDTO> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                BalanceDTO.class
        );

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new UserNotFoundException("User with this token not found");
        }

        return response.getBody();
    }

    public static BalanceDTO increaseBalance(String token, BigDecimal amount) {
        return httpRequestToAccount(token, amount, "/increase");
    }

    public static BalanceDTO decreaseBalance(String token, BigDecimal amount) {
        return httpRequestToAccount(token, amount, "/decrease");
    }

}
