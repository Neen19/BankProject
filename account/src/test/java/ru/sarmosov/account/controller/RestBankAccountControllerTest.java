package ru.sarmosov.account.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.sarmosov.account.exception.InsufficientFundsException;
import ru.sarmosov.account.service.BankAccountService;
import ru.sarmosov.bankstarter.dto.BalanceDTO;
import ru.sarmosov.bankstarter.dto.ErrorResponseDTO;
import ru.sarmosov.bankstarter.dto.TotalDTO;
import ru.sarmosov.bankstarter.exception.UserNotFoundException;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RestBankAccountController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class RestBankAccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BankAccountService bankAccountService;

    @Test
    public void testIncreaseBalance() throws Exception {
        BalanceDTO balanceDTO = new BalanceDTO(new BigDecimal("1000.00"));
        TotalDTO totalDTO = new TotalDTO(new BigDecimal("500.00"));

        when(bankAccountService.increaseBalance(any(String.class), any(TotalDTO.class))).thenReturn(balanceDTO);

        mockMvc.perform(post("/api/account/increase")
                        .header("Authorization", "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\": 500}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"balance\": 1000}"));
    }

    @Test
    public void testDecreaseBalance() throws Exception {
        BalanceDTO balanceDTO = new BalanceDTO(new BigDecimal("500.00"));
        TotalDTO totalDTO = new TotalDTO(new BigDecimal("500.00"));

        when(bankAccountService.decreaseBalance(any(String.class), any(TotalDTO.class))).thenReturn(balanceDTO);

        mockMvc.perform(post("/api/account/decrease")
                        .header("Authorization", "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\": 500}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"balance\": 500}"));
    }

    @Test
    public void testGetBalance() throws Exception {
        BalanceDTO balanceDTO = new BalanceDTO(new BigDecimal("1000.00"));

        when(bankAccountService.getBalance(any(String.class))).thenReturn(balanceDTO);

        mockMvc.perform(get("/api/account/balance")
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"balance\": 1000}"));
    }

    @Test
    public void testHandleUserNotFoundException() throws Exception {
        when(bankAccountService.getBalance(any(String.class))).thenThrow(new UserNotFoundException("User not found"));

        mockMvc.perform(get("/api/account/balance")
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("User not found"));
    }

    @Test
    public void testHandleInsufficientFundsException() throws Exception {
        when(bankAccountService.decreaseBalance(any(String.class), any(TotalDTO.class))).thenThrow(new InsufficientFundsException("Insufficient funds"));

        mockMvc.perform(post("/api/account/decrease")
                        .header("Authorization", "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\": 1000}"))
                .andExpect(status().isBadRequest());
    }
}
