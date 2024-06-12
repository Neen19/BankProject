package ru.sarmosov.deposit.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.sarmosov.deposit.entity.RequestEntity;

@Repository
public interface RequestRepository extends CrudRepository<RequestEntity, Long> {
}
