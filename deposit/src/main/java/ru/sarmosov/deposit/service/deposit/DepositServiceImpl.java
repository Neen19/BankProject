package ru.sarmosov.deposit.service.deposit;


import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import ru.sarmosov.deposit.entity.DepositEntity;
import ru.sarmosov.deposit.repository.DepositRepository;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class DepositServiceImpl implements DepositService {

    private final DepositRepository depositRepository;

    @Override
    @Transactional
    public void refreshBalance(DepositEntity deposit, BigDecimal balance) {
        deposit.setBalance(balance);
        depositRepository.save(deposit);
    }

    @Override
    @Transactional
    public DepositEntity addDeposit(DepositEntity deposit) {
        return depositRepository.save(deposit);
    }
}
