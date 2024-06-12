package ru.sarmosov.customerservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.sarmosov.bankstarter.annotation.Logging;
import ru.sarmosov.customerservice.entity.CustomerEntity;
import ru.sarmosov.customerservice.repository.CustomerRepository;
import ru.sarmosov.customerservice.security.CustomerDetails;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    @Logging(value = "Поиск пользователя по почте")
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<CustomerEntity> customer = customerRepository.findByEmail(username);

        if (customer.isEmpty())
            throw new UsernameNotFoundException("User not found");

        return new CustomerDetails(customer.get());
    }
}
