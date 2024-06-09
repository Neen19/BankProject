package ru.sarmosov.customerservice.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import ru.sarmosov.bankstarter.util.JWTUtil;
import ru.sarmosov.customerservice.dto.AuthDTO;
import ru.sarmosov.bankstarter.dto.CustomerDTO;
import ru.sarmosov.customerservice.dto.TokenResponse;
import ru.sarmosov.customerservice.entity.CustomerEntity;
import ru.sarmosov.customerservice.security.CustomerDetails;


import java.lang.reflect.Field;
import java.util.Date;

@ExtendWith(MockitoExtension.class)
public class JWTServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JWTUtil jwtUtil;

    @Mock
    private CustomerDetailsService customerDetailsService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private JWTService jwtService;

    public static final String CUSTOMER_SUBJECT = "CUSTOMER_SUBJECT";
    public static final String PHONE_NUMBER_CLAIM = "PHONE_NUMBER_CLAIM";
    public static final String BANK_ACCOUNT_ID_CLAIM = "BANK_ACCOUNT_ID_CLAIM";
    public static final String ID_CLAIM = "ID_CLAIM";
    public static final String SECRET = "secret_string";
    public static final String ISSUER = "admin";
    public static final Date DATE = new Date();

    @BeforeEach
    void setUp() throws Exception {
        setPrivateField(jwtService, "CUSTOMER_SUBJECT", CUSTOMER_SUBJECT);
        setPrivateField(jwtService, "PHONE_NUMBER_CLAIM", PHONE_NUMBER_CLAIM);
        setPrivateField(jwtService, "BANK_ACCOUNT_ID_CLAIM", BANK_ACCOUNT_ID_CLAIM);
        setPrivateField(jwtService, "ID_CLAIM", ID_CLAIM);
        setPrivateField(jwtService, "SECRET", SECRET);
        setPrivateField(jwtService, "ISSUER", ISSUER);
    }

    private void setPrivateField(Object targetObject, String fieldName, Object value) throws Exception {
        Field field = targetObject.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(targetObject, value);
    }

    @Test
    void testGetCustomerToken() throws Exception {
        // Given
        AuthDTO authDTO = new AuthDTO();
        authDTO.setPhoneNumber("1234567890");
        authDTO.setPassword("password");

        CustomerDetails customerDetails = mock(CustomerDetails.class);
        when(customerDetailsService.loadUserByUsername(authDTO.getPhoneNumber())).thenReturn(customerDetails);

        CustomerEntity customerEntity = new CustomerEntity();
        when(customerDetails.getCustomerEntity()).thenReturn(customerEntity);

        CustomerDTO customerDTO = new CustomerDTO();
        when(modelMapper.map(customerEntity, CustomerDTO.class)).thenReturn(customerDTO);

        String token = jwtService.generateToken(customerDTO);

        // When
        TokenResponse tokenResponse = jwtService.getCustomerToken(authDTO);

        // Then
        assertNotNull(tokenResponse);
        assertEquals(token, tokenResponse.getToken());

        // Verify
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(customerDetailsService, times(1)).loadUserByUsername(authDTO.getPhoneNumber());
        verify(modelMapper, times(1)).map(customerEntity, CustomerDTO.class);
    }


    @Test
    void testGenerateToken() {

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setPhoneNumber("1234567890");
        customerDTO.setBankAccountId(10L);
        customerDTO.setId(10L);

        Algorithm algorithm = Algorithm.HMAC256(SECRET);

        String expectedToken = JWT.create()
                .withSubject(CUSTOMER_SUBJECT)
                .withClaim(PHONE_NUMBER_CLAIM, customerDTO.getPhoneNumber())
                .withClaim(BANK_ACCOUNT_ID_CLAIM, customerDTO.getBankAccountId())
                .withClaim(ID_CLAIM, customerDTO.getId())
                .withIssuedAt(new Date())
                .withIssuer(ISSUER)
                .sign(Algorithm.HMAC256(SECRET));

        String token = jwtService.generateToken(customerDTO);

        assertNotNull(token);
        assertEquals(expectedToken, token);
    }
}
