package ru.sarmosov.deposit.config;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.util.Pair;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.sarmosov.bankstarter.util.JWTUtil;
import ru.sarmosov.deposit.entity.RequestEntity;
import ru.sarmosov.deposit.factory.DepositFactory;
import ru.sarmosov.deposit.factory.DepositFactoryImpl;
import ru.sarmosov.deposit.factory.RequestFactory;
import ru.sarmosov.deposit.factory.RequestFactoryImpl;
import ru.sarmosov.deposit.repository.*;
import ru.sarmosov.deposit.service.deposit.DepositService;
import ru.sarmosov.deposit.service.deposit.DepositServiceImpl;
import ru.sarmosov.deposit.service.request.RequestService;
import ru.sarmosov.deposit.service.request.RequestServiceImpl;

import java.time.format.DateTimeFormatter;
import java.util.concurrent.*;

@Configuration
@EnableTransactionManagement
public class DepositConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public DateTimeFormatter dateTimeFormatter() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }


    @Bean("futureService")
     ExecutorService futureService() {
        return Executors.newFixedThreadPool(10);
    }

    @Bean("taskService")
    ExecutorService taskService() {
        return Executors.newFixedThreadPool(10);
    }


    @Bean("taskQueue")
    BlockingQueue<RequestEntity> taskQueue() {
        return new ArrayBlockingQueue<>(100);
    }

    @Bean("handledQueue")
    BlockingQueue<RequestEntity> handledQueue() {
        return new ArrayBlockingQueue<>(100);
    }

    @Bean("futureQueue")
    BlockingQueue<Pair<RequestEntity, Future<RequestEntity>>> futureQueue() {
        return new ArrayBlockingQueue<>(100);
    }

    @Bean
    ConcurrentHashMap<Long, Integer> concurrentHashMap() {
        return new ConcurrentHashMap<>();
    }



}
