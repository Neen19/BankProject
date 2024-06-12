package ru.sarmosov.account.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.sarmosov.bankstarter.annotation.Logging;
import ru.sarmosov.bankstarter.dto.BalanceDTO;
import ru.sarmosov.bankstarter.dto.CustomerDTO;
import ru.sarmosov.bankstarter.dto.TotalDTO;
import ru.sarmosov.account.entity.BankAccountEntity;
import ru.sarmosov.account.exception.InsufficientFundsException;
import ru.sarmosov.bankstarter.exception.UserNotFoundException;
import ru.sarmosov.account.repository.BankAccountRepository;
import ru.sarmosov.bankstarter.util.JWTUtil;


@Log4j2
@Service
@RequiredArgsConstructor
public class BankAccountService implements BankAccountInterface {



    private final BankAccountRepository bankAccountRepository;
    private final JWTUtil jwtUtil;

    @Logging(value = "Получение баланса пользователя")
    public BalanceDTO getBalance(String header) {
        String token = getTokenFromHeader(header);
        CustomerDTO dto = jwtUtil.verifyTokenAndRetrievePhoneNumber(token);
        BankAccountEntity account = bankAccountRepository.findById(dto.getId()).orElseThrow(UserNotFoundException::new);
        return new BalanceDTO(account.getBalance());
    }


    @Logging(value = "Пополнение баланса")
    public BalanceDTO increaseBalance(String header, TotalDTO totalDTO) {
        String token = getTokenFromHeader(header);
        CustomerDTO customerDTO = jwtUtil.verifyTokenAndRetrievePhoneNumber(token);
        System.out.println(customerDTO.toString());
        BankAccountEntity account = bankAccountRepository.findById(customerDTO.getBankAccountId()).orElseThrow(
                UserNotFoundException::new);
        account.setBalance(account.getBalance().add(totalDTO.getTotal()));
        bankAccountRepository.save(account);
        return new BalanceDTO(account.getBalance());
    }


//    @Logging(value = "Уменьшение баланса")
    public BalanceDTO decreaseBalance(String header, TotalDTO totalDTO) throws IllegalArgumentException {
        String token = getTokenFromHeader(header);
        CustomerDTO customerDTO = jwtUtil.verifyTokenAndRetrievePhoneNumber(token);
        BankAccountEntity account = bankAccountRepository.findById(customerDTO.getBankAccountId()).orElseThrow(
                UserNotFoundException::new);
        if (account.getBalance().compareTo(totalDTO.getTotal()) < 0) {
            throw new InsufficientFundsException("Insufficient funds on account balance");
        }
        account.setBalance(account.getBalance().subtract(totalDTO.getTotal()));
        bankAccountRepository.save(account);
        return new BalanceDTO(account.getBalance());
    }

    @Logging(value = "Получение токена из тела хэдера")
    private String getTokenFromHeader (String header) {
        return header.replace("Bearer ", "");
    }

}
