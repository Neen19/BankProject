package ru.sarmosov.deposit.service.deposit;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sarmosov.bankstarter.annotation.Logging;
import ru.sarmosov.bankstarter.enums.PercentPaymentPeriod;
import ru.sarmosov.deposit.entity.PercentPaymentPeriodEntity;
import ru.sarmosov.deposit.repository.PercentPaymentPeriodRepository;

@Service
@RequiredArgsConstructor
public class PercentPaymentPeriodServiceImpl implements PercentPaymentPeriodService{

    private final PercentPaymentPeriodRepository repository;

    @Logging
    public PercentPaymentPeriodEntity getPersistenceEntity(PercentPaymentPeriod period) {
        return repository.findByPeriod(period).orElseThrow();
    }

}
