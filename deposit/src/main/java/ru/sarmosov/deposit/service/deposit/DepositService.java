package ru.sarmosov.deposit.service.deposit;

import ru.sarmosov.bankstarter.dto.DepositTotalDTO;
import ru.sarmosov.deposit.entity.DepositEntity;
import ru.sarmosov.deposit.exception.DepositNotFountException;

import java.math.BigDecimal;
import java.util.List;

public interface DepositService {

    void refreshBalance(DepositEntity deposit, BigDecimal balance);

    DepositEntity addDeposit(DepositEntity deposit);

    DepositEntity shutDownDeposit(Long depositId);

    DepositTotalDTO increaseBalance(DepositTotalDTO depositTotalDTO, String token) throws DepositNotFountException;

    DepositTotalDTO decreaseBalance(DepositTotalDTO depositTotalDTO, String token) throws DepositNotFountException;

    List<DepositEntity> getCustomerDeposits(String token);


}
