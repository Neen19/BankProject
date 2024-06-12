package ru.sarmosov.deposit.service.deposit;

import ru.sarmosov.bankstarter.enums.PercentPaymentPeriod;
import ru.sarmosov.deposit.entity.PercentPaymentPeriodEntity;

public interface PercentPaymentPeriodService {

    PercentPaymentPeriodEntity getPersistenceEntity(PercentPaymentPeriod period);

}
