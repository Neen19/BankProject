package ru.sarmosov.customerservice.controller;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.web.servlet.MockMvc;
import ru.sarmosov.bankstarter.dto.AuthDTO;
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


    @MockBean
    private JWTFilter jwtFilter;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JWTService jwtService;


    @Test
    public void testLoginSuccess() throws Exception {
        AuthDTO authDTO = new AuthDTO("user", "password");
        TokenResponseDTO tokenResponseDTO = new TokenResponseDTO("token");

        when(jwtService.getCustomerToken(any(AuthDTO.class))).thenReturn(tokenResponseDTO);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"user\", \"password\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"token\":\"token\"}"));
    }

    @Test
    public void testLoginBadCredentials() throws Exception {
        when(jwtService.getCustomerToken(any(AuthDTO.class))).thenThrow(new BadCredentialsException("User not found"));

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"user\", \"password\":\"wrongpassword\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("User not found"));
    }
}
