package ru.sarmosov.deposit.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.sarmosov.deposit.entity.PercentPaymentTypeEntity;

@Repository
public interface PercentPaymentTypeRepository extends CrudRepository<PercentPaymentTypeEntity, Integer> {
}
