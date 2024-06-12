package ru.sarmosov.customerservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.sarmosov.customerservice.entity.CustomerEntity;

import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<CustomerEntity, Long> {

    Optional<CustomerEntity> findByEmail(String email);

}
