package ru.sarmosov.customerservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.sarmosov.customerservice.entity.Customer;

import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    public Optional<Customer> findByPhoneNumber(String phoneNumber);

}
