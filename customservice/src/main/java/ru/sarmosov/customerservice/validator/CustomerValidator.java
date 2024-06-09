package ru.sarmosov.customerservice.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.sarmosov.customerservice.entity.CustomerEntity;
import ru.sarmosov.customerservice.service.CustomerDetailsService;

@Component
public class CustomerValidator implements Validator {

    private final CustomerDetailsService customerDetailsService;

    @Autowired
    public CustomerValidator(CustomerDetailsService customerDetailsService) {
        this.customerDetailsService = customerDetailsService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return CustomerEntity.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        CustomerEntity customerEntity = (CustomerEntity) o;

        try {
            customerDetailsService.loadUserByUsername(customerEntity.getPhoneNumber());
        } catch (UsernameNotFoundException ignored) {
            return;
        }

        errors.rejectValue("username", "", "Человек с таким именем пользователя уже существует");
    }
}
