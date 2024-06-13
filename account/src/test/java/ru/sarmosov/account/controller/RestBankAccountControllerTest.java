package ru.sarmosov.account.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
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
import static org.mockito.ArgumentMatchers.anyString;
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

    private ObjectMapper objectMapper = new ObjectMapper();

    private String token = "Bearer some-token";


    @Test
    public void testIncreaseBalance() throws Exception {
        TotalDTO totalDTO = new TotalDTO();
        totalDTO.setTotal(new BigDecimal(100));
        BalanceDTO balanceDTO = new BalanceDTO();
        balanceDTO.setBalance(new BigDecimal(110));

        Mockito.when(bankAccountService.increaseBalance(anyString(), any(TotalDTO.class)))
                .thenReturn(balanceDTO);

        mockMvc.perform(post("/api/account/increase")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(totalDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(balanceDTO)));
    }

    @Test
    public void testDecreaseBalance() throws Exception {
        TotalDTO totalDTO = new TotalDTO();
        totalDTO.setTotal(new BigDecimal(100));
        BalanceDTO balanceDTO = new BalanceDTO();
        balanceDTO.setBalance(new BigDecimal(900));

        Mockito.when(bankAccountService.decreaseBalance(anyString(), any(TotalDTO.class)))
                .thenReturn(balanceDTO);

        mockMvc.perform(post("/api/account/decrease")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(totalDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(balanceDTO)));
    }

    @Test
    public void testGetBalance() throws Exception {
        BalanceDTO balanceDTO = new BalanceDTO();
        balanceDTO.setBalance(new BigDecimal(1000));

        Mockito.when(bankAccountService.getBalance(anyString()))
                .thenReturn(balanceDTO);

        mockMvc.perform(get("/api/account/balance")
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(balanceDTO)));
    }

    @Test
    public void testHandleUserNotFoundException() throws Exception {
        Mockito.when(bankAccountService.getBalance(anyString()))
                .thenThrow(new UserNotFoundException("User not found"));

        mockMvc.perform(get("/api/account/balance")
                        .header("Authorization", token))
                .andExpect(status().isForbidden())
                .andExpect(content().string("User not found"));
    }

    @Test
    public void testHandleInsufficientFundsException() throws Exception {
        TotalDTO totalDTO = new TotalDTO();
        totalDTO.setTotal(new BigDecimal(100));

        Mockito.when(bankAccountService.decreaseBalance(anyString(), any(TotalDTO.class)))
                .thenThrow(new InsufficientFundsException("Insufficient funds"));

        mockMvc.perform(post("/api/account/decrease")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(totalDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(objectMapper.writeValueAsString(new ErrorResponseDTO("Insufficient funds"))));
    }

}