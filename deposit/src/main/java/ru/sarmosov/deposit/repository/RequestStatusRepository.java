package ru.sarmosov.deposit.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.sarmosov.deposit.entity.DepositEntity;
import ru.sarmosov.deposit.entity.RequestEntity;
import ru.sarmosov.deposit.entity.RequestStatusEntity;
import ru.sarmosov.bankstarter.enums.RequestStatus;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestStatusRepository extends CrudRepository<RequestStatusEntity, Integer> {

    Optional<RequestStatusEntity> findByStatus(RequestStatus status);


}
