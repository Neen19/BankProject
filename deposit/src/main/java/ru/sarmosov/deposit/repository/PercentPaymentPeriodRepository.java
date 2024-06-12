package ru.sarmosov.deposit.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.sarmosov.bankstarter.enums.PercentPaymentPeriod;
import ru.sarmosov.bankstarter.enums.PercentPaymentType;
import ru.sarmosov.deposit.entity.PercentPaymentPeriodEntity;

import java.util.Optional;

@Repository
public interface PercentPaymentPeriodRepository extends CrudRepository<PercentPaymentPeriodEntity, Integer> {

    Optional<PercentPaymentPeriodEntity> findByPeriod(PercentPaymentPeriod period);
}
