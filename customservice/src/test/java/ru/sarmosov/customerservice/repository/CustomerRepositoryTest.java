package ru.sarmosov.customerservice.repository;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.sarmosov.customerservice.config.IntegrationTestConfig;
import ru.sarmosov.customerservice.entity.Customer;

import java.util.Optional;

@SpringJUnitConfig(IntegrationTestConfig.class)
@DataJpaTest
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository userRepository;

    @BeforeEach
            void setUp() {
        Customer customer = Customer.builder()
                .id(1L)
                .bankAccountId(1L)
                .phoneNumber("88005553535")
                .password("password")
                .build();
        userRepository.save(customer);
    }

    @Test
    public void CustomerFoundFindByNumberTest() {

        Optional<Customer> customerOptional = userRepository.findByPhoneNumber("88005553535");
        assertTrue(customerOptional.isPresent());
        assertEquals("88005553535", customerOptional.get().getPhoneNumber());
    }

    @Test
    public void CustomerNotFoundFindByNumberTest() {
        Optional<Customer> customerOptional = userRepository.findByPhoneNumber("123");
        assertFalse(customerOptional.isPresent());
    }
}