package ru.sarmosov.account.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.sarmosov.bankstarter.dto.CustomerDTO;
import ru.sarmosov.bankstarter.exception.UserNotFoundException;

@Component
public class NetworkUtils {

    private final RestTemplate restTemplate;

    public NetworkUtils(RestTemplate restTemplate) throws UserNotFoundException {
        this.restTemplate = restTemplate;
    }

    @Value("${customer-service.get-customer-url}")
    private String GET_CUSTOMER_URL;

    private final String AUTHORIZATION_HEADER = "Authorization";

    public CustomerDTO getCustomerDTOByToken(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION_HEADER, token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<CustomerDTO> response = restTemplate.exchange(
                GET_CUSTOMER_URL,
                HttpMethod.GET,
                entity,
                CustomerDTO.class
        );

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new UserNotFoundException("User with this token not found");
        }

        return response.getBody();
    }
}
