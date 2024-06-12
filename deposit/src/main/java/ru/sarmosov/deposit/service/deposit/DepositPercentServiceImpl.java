package ru.sarmosov.deposit.service.deposit;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.sarmosov.deposit.deposit.AbstractDeposit;
import ru.sarmosov.deposit.entity.DepositEntity;
import ru.sarmosov.deposit.factory.DepositFactory;
import ru.sarmosov.deposit.repository.DepositRepository;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class DepositPercentServiceImpl implements DepositPercentService {

    private final DepositRepository repository;
    private final DepositFactory depositFactory;
    private final DepositService depositService;

    @Scheduled(cron = "00 00 12 * * ?",  zone = "Europe/Moscow")
    public void payPercent() {
        System.out.println("call");
        Iterable<DepositEntity> deposits = repository.findNotEnded();
        deposits.forEach(this::refreshBalance);
    }

    private void refreshBalance(DepositEntity deposit) {
        AbstractDeposit abstractDeposit = depositFactory.convertDepositEntityToAbstractDeposit(deposit);
        BigDecimal newBalance = abstractDeposit.payPercent();
        depositService.refreshBalance(deposit, newBalance);
    }


}
