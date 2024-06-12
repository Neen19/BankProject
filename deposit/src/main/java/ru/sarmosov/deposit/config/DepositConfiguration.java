package ru.sarmosov.deposit.config;

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

    private final RequestRepository requestRepository;
    private final RequestStatusRepository requestStatusRepository;
    private final JWTUtil jwtUtil;

    public DepositConfiguration(RequestRepository requestRepository, RequestStatusRepository requestStatusRepository, JWTUtil jwtUtil) {
        this.requestRepository = requestRepository;
        this.requestStatusRepository = requestStatusRepository;
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public DateTimeFormatter dateTimeFormatter() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    @Bean
    public DepositFactory factory(PercentPaymentPeriodRepository percentPaymentPeriodRepository, DepositTypeRepository depositTypeRepository) {
        return new DepositFactoryImpl(percentPaymentPeriodRepository, depositTypeRepository);
    }

    @Bean
    DepositService depositService(DepositRepository depositRepository) {
        return new DepositServiceImpl(depositRepository);
    }

    @Bean("futureService")
     ExecutorService futureService() {
        return Executors.newFixedThreadPool(10);
    }

    @Bean("taskService")
    ExecutorService taskService() {
        return Executors.newFixedThreadPool(10);
    }


    @Bean
    RequestService requestService() {
        return new RequestServiceImpl(requestRepository, requestStatusRepository);
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

//    @Bean
//    ControllerService controllerService(RequestFactory factory) {
//        return new ControllerServiceImpl(factory, requestHandler);
//    }

    @Bean
    RequestFactory requestFactory(DepositFactory factory) {
        return new RequestFactoryImpl(jwtUtil, requestStatusRepository, requestService());
    }

}
