package ru.sarmosov.customerservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.sarmosov.customerservice.entity.CustomerEntity;
import ru.sarmosov.customerservice.repository.CustomerRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerEntityDetailsTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerDetailsService customerDetailsService;


    @Test
    public void testLoadUserByUsername_UserFound() {

        String phoneNumber = "1234567890";
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setPhoneNumber(phoneNumber);

        when(customerRepository.findByPhoneNumber(phoneNumber)).thenReturn(Optional.of(customerEntity));

        UserDetails userDetails = customerDetailsService.loadUserByUsername(phoneNumber);

        assertNotNull(userDetails);
        assertEquals(phoneNumber, userDetails.getUsername());
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {

        String phoneNumber = "1234567890";

        when(customerRepository.findByPhoneNumber(phoneNumber)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            customerDetailsService.loadUserByUsername(phoneNumber);
        });
    }

}
