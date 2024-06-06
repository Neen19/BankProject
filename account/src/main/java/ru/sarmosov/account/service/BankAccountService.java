package ru.sarmosov.account.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sarmosov.account.dto.CustomerDTO;
import ru.sarmosov.account.dto.TotalDTO;
import ru.sarmosov.account.entity.BankAccount;
import ru.sarmosov.account.exception.InsufficientFundsException;
import ru.sarmosov.account.exception.UserNotFoundException;
import ru.sarmosov.account.repository.BankAccountRepository;
import ru.sarmosov.account.util.NetworkUtils;

import java.math.BigDecimal;


@Log4j2
@Service
@RequiredArgsConstructor
public class BankAccountService implements BankAccountInterface {

    private final BankAccountRepository bankAccountRepository;
    private final NetworkUtils networkUtils;

    public BigDecimal getBalance(Long id) {
        BankAccount account = bankAccountRepository.findById(id).orElseThrow(UserNotFoundException::new);
        return account.getBalance();
    }

    @Transactional
    public void increaseBalance(String token, TotalDTO totalDTO) {
        CustomerDTO customerDTO = networkUtils.getCustomerDTOByToken(token);
        BankAccount account = bankAccountRepository.findById(customerDTO.getBankAccountId()).orElseThrow(
                UserNotFoundException::new);
        account.setBalance(account.getBalance().add(totalDTO.getTotal()));
        bankAccountRepository.save(account);
    }

    @Transactional
    public void decreaseBalance(String token, TotalDTO totalDTO) {
        log.info("my message");
        CustomerDTO customerDTO = networkUtils.getCustomerDTOByToken(token);
        BankAccount account = bankAccountRepository.findById(customerDTO.getBankAccountId()).orElseThrow(
                UserNotFoundException::new);
        if (account.getBalance().compareTo(totalDTO.getTotal()) < 0) {
            throw new InsufficientFundsException("Insufficient funds on account balance");
        }
        account.setBalance(account.getBalance().subtract(totalDTO.getTotal()));
        bankAccountRepository.save(account);
    }

}
