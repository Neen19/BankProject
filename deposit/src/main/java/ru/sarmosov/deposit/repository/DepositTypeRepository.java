package ru.sarmosov.deposit.repository;

import org.springframework.data.repository.CrudRepository;
import ru.sarmosov.bankstarter.enums.DepositType;
import ru.sarmosov.deposit.entity.DepositTypeEntity;

import java.util.Optional;

public interface DepositTypeRepository extends CrudRepository<DepositTypeEntity, Integer> {

    public Optional<DepositTypeEntity> findByTypeName(DepositType name);

}
