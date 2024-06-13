package ru.sarmosov.deposit.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.sarmosov.deposit.entity.RequestEntity;

import java.util.List;

@Repository
public interface RequestRepository extends CrudRepository<RequestEntity, Long> {

    List<RequestEntity> findAllByCustomerId(Long token);

}
