package ru.sarmosov.deposit.repository;

import org.springframework.data.repository.CrudRepository;
import ru.sarmosov.deposit.entity.DepositTypeEntity;

public interface DepositTypeRepository extends CrudRepository<DepositTypeEntity, Integer> {

}
