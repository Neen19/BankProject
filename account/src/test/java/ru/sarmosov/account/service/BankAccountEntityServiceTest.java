package ru.sarmosov.account.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.sarmosov.bankstarter.dto.BalanceDTO;
import ru.sarmosov.account.entity.BankAccountEntity;
import ru.sarmosov.account.exception.InsufficientFundsException;
import ru.sarmosov.bankstarter.exception.UserNotFoundException;
import ru.sarmosov.account.repository.BankAccountRepository;
import ru.sarmosov.bankstarter.dto.CustomerDTO;
import ru.sarmosov.bankstarter.dto.TotalDTO;
import ru.sarmosov.bankstarter.util.JWTUtil;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BankAccountEntityServiceTest {

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Mock
    private JWTUtil jwtUtil;

    @InjectMocks
    private BankAccountService bankAccountService;

    private String token;
    private String header;
    private CustomerDTO customerDTO;
    private BankAccountEntity bankAccountEntity;

    @BeforeEach
    public void setup() {
        token = "sample_token";
        header = "Bearer " + token;
        customerDTO = new CustomerDTO(1L, 1L, "1234567890");
        bankAccountEntity = new BankAccountEntity();
        bankAccountEntity.setId(1L);
        bankAccountEntity.setBalance(BigDecimal.valueOf(1000));

        when(jwtUtil.verifyTokenAndRetrievePhoneNumber(token)).thenReturn(customerDTO);
        when(bankAccountRepository.findById(1L)).thenReturn(Optional.of(bankAccountEntity));
    }

    @Test
    public void testGetBalance() {
        BalanceDTO balance = bankAccountService.getBalance(header);
        assertEquals(BigDecimal.valueOf(1000), balance.getBalance());
    }

    @Test
    public void testIncreaseBalance() {
        TotalDTO totalDTO = new TotalDTO();
        totalDTO.setTotal(BigDecimal.valueOf(200));

        bankAccountService.increaseBalance(header, totalDTO);

        assertEquals(BigDecimal.valueOf(1200), bankAccountEntity.getBalance());
        verify(bankAccountRepository, times(1)).save(bankAccountEntity);
    }

    @Test
    public void testDecreaseBalance() {
        TotalDTO totalDTO = new TotalDTO();
        totalDTO.setTotal(BigDecimal.valueOf(200));

        bankAccountService.decreaseBalance(header, totalDTO);

        assertEquals(BigDecimal.valueOf(800), bankAccountEntity.getBalance());
        verify(bankAccountRepository, times(1)).save(bankAccountEntity);
    }

    @Test
    public void testDecreaseBalanceInsufficientFunds() {
        TotalDTO totalDTO = new TotalDTO();
        totalDTO.setTotal(BigDecimal.valueOf(2000));

        assertThrows(InsufficientFundsException.class, () -> {
            bankAccountService.decreaseBalance(header, totalDTO);
        });
    }

    @Test
    public void testGetBalanceUserNotFound() {
        when(bankAccountRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            bankAccountService.getBalance(header);
        });
    }

    @Test
    public void testIncreaseBalanceUserNotFound() {
        when(bankAccountRepository.findById(1L)).thenReturn(Optional.empty());

        TotalDTO totalDTO = new TotalDTO();
        totalDTO.setTotal(BigDecimal.valueOf(200));

        assertThrows(UserNotFoundException.class, () -> {
            bankAccountService.increaseBalance(header, totalDTO);
        });
    }

    @Test
    public void testDecreaseBalanceUserNotFound() {
        when(bankAccountRepository.findById(1L)).thenReturn(Optional.empty());

        TotalDTO totalDTO = new TotalDTO();
        totalDTO.setTotal(BigDecimal.valueOf(200));

        assertThrows(UserNotFoundException.class, () -> {
            bankAccountService.decreaseBalance(header, totalDTO);
        });
    }
}

