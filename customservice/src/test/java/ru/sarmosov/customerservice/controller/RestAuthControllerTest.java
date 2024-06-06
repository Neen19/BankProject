package ru.sarmosov.customerservice.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import ru.sarmosov.customerservice.dto.AuthDTO;
import ru.sarmosov.customerservice.dto.CustomerDTO;
import ru.sarmosov.customerservice.dto.TokenResponse;
import ru.sarmosov.customerservice.entity.Customer;
import ru.sarmosov.customerservice.service.CustomerDetailsService;
import ru.sarmosov.customerservice.service.JWTService;
import ru.sarmosov.customerservice.util.JWTUtil;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(RestAuthController.class)
@AutoConfigureMockMvc(addFilters = false)
public class RestAuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JWTService jwtService;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    CustomerDetailsService customerDetailsService;

    @MockBean
    JWTUtil jwtUtil;

    @InjectMocks
    private RestAuthController restAuthController;


    @Test
    public void testLogin_Success() throws Exception {
        AuthDTO authDTO = new AuthDTO();
        authDTO.setPhoneNumber("1234567890");
        authDTO.setPassword("password");

        TokenResponse tokenResponse = new TokenResponse("generated-token");

        when(jwtService.getCustomerToken(any(AuthDTO.class))).thenReturn(tokenResponse);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("generated-token"));

        verify(jwtService).getCustomerToken(any(AuthDTO.class));
    }

    @Test
    public void testLogin_BadCredentials() throws Exception {
        AuthDTO authDTO = new AuthDTO();
        authDTO.setPhoneNumber("1234567890");
        authDTO.setPassword("wrong-password");

        when(jwtService.getCustomerToken(any(AuthDTO.class))).thenThrow(new BadCredentialsException("Bad credentials"));

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authDTO)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("User not found"));

        verify(jwtService).getCustomerToken(any(AuthDTO.class));
    }

    @Test
    public void testDecode_Success() throws Exception {
        String token = "Bearer valid-token";
        Customer customer = new Customer();
        customer.setPhoneNumber("1234567890");
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setPhoneNumber("1234567890");

        when(jwtService.getCustomerByToken(anyString())).thenReturn(customer);
        when(modelMapper.map(any(Customer.class), eq(CustomerDTO.class))).thenReturn(customerDTO);

        mockMvc.perform(get("/api/auth/customer")
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.phoneNumber").value("1234567890"));

        verify(jwtService).getCustomerByToken(anyString());
        verify(modelMapper).map(any(Customer.class), eq(CustomerDTO.class));
    }

    @Test
    public void testDecode_BadCredentials() throws Exception {
        String token = "Bearer invalid-token";

        when(jwtService.getCustomerByToken(anyString())).thenThrow(new BadCredentialsException("Invalid JWT token"));

        mockMvc.perform(get("/api/auth/customer")
                        .header("Authorization", token))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("User not found"));

        verify(jwtService).getCustomerByToken(anyString());
    }
}

