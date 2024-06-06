package ru.sarmosov.customerservice.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import com.auth0.jwt.exceptions.JWTVerificationException;
import ru.sarmosov.customerservice.dto.AuthDTO;
import ru.sarmosov.customerservice.dto.TokenResponse;
import ru.sarmosov.customerservice.entity.Customer;
import ru.sarmosov.customerservice.security.CustomerDetails;
import ru.sarmosov.customerservice.util.JWTUtil;


@ExtendWith(MockitoExtension.class)
public class JWTServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JWTUtil jwtUtil;

    @Mock
    private CustomerDetailsService customerDetailsService;

    @InjectMocks
    private JWTService jwtService;


    @Test
    public void testGetCustomerToken_Success() {

        AuthDTO authDTO = new AuthDTO();
        authDTO.setPhoneNumber("1234567890");
        authDTO.setPassword("password");

        String token = "generated-token";
        when(jwtUtil.generateToken(authDTO.getPhoneNumber())).thenReturn(token);

        TokenResponse tokenResponse = jwtService.getCustomerToken(authDTO);

        assertNotNull(tokenResponse);
        assertEquals(token, tokenResponse.getToken());

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtil).generateToken(authDTO.getPhoneNumber());
    }

    @Test
    public void testGetCustomerToken_BadCredentials() {

        AuthDTO authDTO = new AuthDTO();
        authDTO.setPhoneNumber("1234567890");
        authDTO.setPassword("wrong-password");

        doThrow(new BadCredentialsException("Bad credentials")).when(authenticationManager)
                .authenticate(any(UsernamePasswordAuthenticationToken.class));

        assertThrows(BadCredentialsException.class, () -> {
            jwtService.getCustomerToken(authDTO);
        });

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtil, never()).generateToken(anyString());
    }

    @Test
    public void testGetCustomerByToken_Success() {

        String token = "Bearer valid-token";
        String phoneNumber = "1234567890";
        String jwtToken = token.replace("Bearer ", "");

        Customer customer = new Customer();
        customer.setPhoneNumber(phoneNumber);

        CustomerDetails customerDetails = new CustomerDetails(customer);

        when(jwtUtil.verifyTokenAndRetrievePhoneNumber(jwtToken)).thenReturn(phoneNumber);
        when(customerDetailsService.loadUserByUsername(phoneNumber)).thenReturn(customerDetails);

        Customer result = jwtService.getCustomerByToken(token);

        assertNotNull(result);
        assertEquals(phoneNumber, result.getPhoneNumber());

        verify(jwtUtil).verifyTokenAndRetrievePhoneNumber(jwtToken);
        verify(customerDetailsService).loadUserByUsername(phoneNumber);
    }

    @Test
    public void testGetCustomerByToken_InvalidToken() {

        String token = "Bearer invalid-token";
        String jwtToken = token.replace("Bearer ", "");

        when(jwtUtil.verifyTokenAndRetrievePhoneNumber(jwtToken)).thenThrow(new JWTVerificationException("Invalid token"));

        assertThrows(BadCredentialsException.class, () -> {
            jwtService.getCustomerByToken(token);
        });

        verify(jwtUtil).verifyTokenAndRetrievePhoneNumber(jwtToken);
        verify(customerDetailsService, never()).loadUserByUsername(anyString());
    }
}
