package ru.sarmosov.customerservice.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.web.servlet.MockMvc;
import ru.sarmosov.bankstarter.dto.AuthDTO;
import ru.sarmosov.bankstarter.dto.ErrorResponseDTO;
import ru.sarmosov.bankstarter.dto.TokenResponseDTO;
import ru.sarmosov.customerservice.filter.JWTFilter;
import ru.sarmosov.customerservice.service.JWTService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = RestAuthController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class RestAuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JWTService jwtService;

    @MockBean
    private JWTFilter jwtFilter;

    private ObjectMapper objectMapper = new ObjectMapper();



    @Test
    public void testLoginSuccess() throws Exception {
        AuthDTO authDTO = new AuthDTO();
        authDTO.setEmail("testuser@mail.ru");
        authDTO.setPassword("password");

        TokenResponseDTO tokenResponseDTO = new TokenResponseDTO();
        tokenResponseDTO.setToken("test-token");

        Mockito.when(jwtService.getCustomerToken(any(AuthDTO.class)))
                .thenReturn(tokenResponseDTO);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(tokenResponseDTO)));
    }

    @Test
    public void testLoginFailure() throws Exception {
        AuthDTO authDTO = new AuthDTO();
        authDTO.setEmail("testuser@mail.ru");
        authDTO.setPassword("password");

        Mockito.when(jwtService.getCustomerToken(any(AuthDTO.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(objectMapper.writeValueAsString(new ErrorResponseDTO("User not found"))));
    }
}

