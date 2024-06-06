package ru.sarmosov.customerservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.sarmosov.customerservice.repository.CustomerRepository;
import ru.sarmosov.customerservice.service.CustomerDetailsService;
import ru.sarmosov.customerservice.service.JWTService;
import ru.sarmosov.customerservice.util.JWTUtil;

import javax.sql.DataSource;

@TestConfiguration
public class IntegrationTestConfig {

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:schema.sql") // Если нужно выполнить скрипт инициализации базы данных
                .build();
    }

}
