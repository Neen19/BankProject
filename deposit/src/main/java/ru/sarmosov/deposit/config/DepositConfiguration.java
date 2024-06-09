package ru.sarmosov.deposit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.sarmosov.deposit.factory.DepositFactory;
import ru.sarmosov.deposit.factory.DepositFactoryImpl;
import ru.sarmosov.deposit.repository.DepositTypeRepository;
import ru.sarmosov.deposit.repository.PercentPaymentTypeRepository;

import java.time.format.DateTimeFormatter;

@Configuration
public class DepositConfiguration {

    @Bean
    public DateTimeFormatter dateTimeFormatter() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    @Bean
    public DepositFactory factory(PercentPaymentTypeRepository percentPaymentTypeRepository, DepositTypeRepository depositTypeRepository) {
        return new DepositFactoryImpl(percentPaymentTypeRepository, depositTypeRepository);
    }

}
