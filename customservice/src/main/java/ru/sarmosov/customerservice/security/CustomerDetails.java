package ru.sarmosov.customerservice.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.sarmosov.customerservice.entity.CustomerEntity;

import java.util.Collection;
import java.util.List;

@Getter
public class CustomerDetails implements UserDetails {

    private CustomerEntity customerEntity;

    public CustomerDetails(CustomerEntity customerEntity) {
        this.customerEntity = customerEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return customerEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return customerEntity.getPhoneNumber();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
