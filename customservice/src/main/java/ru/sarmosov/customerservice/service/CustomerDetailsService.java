package ru.sarmosov.customerservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.sarmosov.customerservice.entity.Customer;
import ru.sarmosov.customerservice.repository.CustomerRepository;
import ru.sarmosov.customerservice.security.CustomerDetails;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Customer> customer = customerRepository.findByPhoneNumber(username);
        if (customer.isEmpty())
            throw new UsernameNotFoundException("User not found");

        return new CustomerDetails(customer.get());
    }
}
