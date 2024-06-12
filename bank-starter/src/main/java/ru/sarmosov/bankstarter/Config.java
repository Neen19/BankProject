package ru.sarmosov.bankstarter;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.sarmosov.bankstarter.aspect.LogAspect;
import ru.sarmosov.bankstarter.aspect.ValidationExceptionHandler;
import ru.sarmosov.bankstarter.properties.BankStarterProperties;
import ru.sarmosov.bankstarter.util.JWTUtil;

@Configuration
@EnableConfigurationProperties(BankStarterProperties.class)
@RequiredArgsConstructor
public class Config {

    private final BankStarterProperties properties;


    @Bean
    public JWTUtil jwtUtil() {
        return new JWTUtil(properties);
    }

    @Bean
    LogAspect logAspect() {
        return new LogAspect();
    }

    @Bean
    ValidationExceptionHandler handler() {
        return new ValidationExceptionHandler();
    }

}
