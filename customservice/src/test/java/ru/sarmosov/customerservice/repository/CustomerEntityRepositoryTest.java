package ru.sarmosov.customerservice.repository;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.sarmosov.customerservice.config.IntegrationTestConfig;
import ru.sarmosov.customerservice.entity.CustomerEntity;

import java.util.Optional;

@SpringJUnitConfig(IntegrationTestConfig.class)
@DataJpaTest
public class CustomerEntityRepositoryTest {

    @Autowired
    private CustomerRepository userRepository;

    @BeforeEach
            void setUp() {
        CustomerEntity customerEntity = new CustomerEntity(
                1L,
                "88005553535",
                "password"
        );
        userRepository.save(customerEntity);
    }

    @Test
    public void CustomerFoundFindByNumberTest() {

        Optional<CustomerEntity> customerOptional = userRepository.findByEmail("88005553535");
        assertTrue(customerOptional.isPresent());
        assertEquals("88005553535", customerOptional.get().getEmail());
    }

    @Test
    public void CustomerNotFoundFindByNumberTest() {
        Optional<CustomerEntity> customerOptional = userRepository.findByEmail("123");
        assertFalse(customerOptional.isPresent());
    }
}