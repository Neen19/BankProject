package ru.sarmosov.account.controller;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.sarmosov.account.exception.InsufficientFundsException;
import ru.sarmosov.bankstarter.exception.UserNotFoundException;
import ru.sarmosov.account.service.BankAccountService;
import ru.sarmosov.bankstarter.dto.TotalDTO;

import java.math.BigDecimal;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(RestBankAccountController.class)
public class RestBankAccountEntityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BankAccountService bankAccountService;

    @InjectMocks
    RestBankAccountController controller;

    private final TotalDTO totalDTO = new TotalDTO(new BigDecimal("100.0"));
    private final String token = "Bearer sample_token";


    @Test
    public void testIncrease() throws Exception {

        mockMvc.perform(post("/api/account/increase")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"total\":100.0}"))
                .andExpect(status().isOk());

        verify(bankAccountService).increaseBalance(token, totalDTO);
    }

    @Test
    public void testDecrease() throws Exception {
        mockMvc.perform(post("/api/account/decrease")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"total\":100.0}"))
                .andExpect(status().isOk());

        verify(bankAccountService).decreaseBalance(token, totalDTO);
    }

    @Test
    public void testDecreaseInsufficientFunds() throws Exception {
        doThrow(new InsufficientFundsException("Insufficient funds on account balance"))
                .when(bankAccountService).decreaseBalance(token, totalDTO);

        mockMvc.perform(post("/api/account/decrease")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"total\":100}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Insufficient funds on account balance"));
    }

    @Test
    public void testUserNotFound() throws Exception {

        doThrow(new UserNotFoundException("User not found"))
                .when(bankAccountService).increaseBalance(token, totalDTO);

        mockMvc.perform(post("/api/account/increase")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"total\":100}"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("User not found"));
    }
}

