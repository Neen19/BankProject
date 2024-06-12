package ru.sarmosov.deposit.service.handler;


import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.sarmosov.bankstarter.enums.RequestStatus;
import ru.sarmosov.bankstarter.exception.InsufficientFundsException;
import ru.sarmosov.deposit.entity.DepositEntity;
import ru.sarmosov.deposit.entity.RequestEntity;
import ru.sarmosov.deposit.exception.LittleDepositException;
import ru.sarmosov.deposit.factory.DepositFactory;
import ru.sarmosov.deposit.service.deposit.DepositService;
import ru.sarmosov.deposit.service.request.RequestService;
import ru.sarmosov.deposit.util.NetworkUtils;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;

@Service
public class Consumer {

    private final RequestService requestService;
    private final BlockingQueue<RequestEntity> handledQueue;
    private final RequestHandler handler;
    private final DepositService depositService;
    private final DepositFactory depositFactory;

    public Consumer(@Qualifier("handledQueue") BlockingQueue<RequestEntity> handledQueue, RequestService requestService, RequestHandler handler, DepositService depositService, DepositFactory depositFactory) {
        this.handledQueue = handledQueue;
        this.requestService = requestService;
        this.handler = handler;
        this.depositService = depositService;
        this.depositFactory = depositFactory;
    }

    private Iterable<RequestEntity> iter;

    @Scheduled(fixedDelay = 5000)
    public void consume() throws InterruptedException {
        if (!handledQueue.isEmpty()) {
            RequestEntity entity = handledQueue.take();
            if (entity.getAmount().compareTo(new BigDecimal("10000")) < 0) {
                throw new LittleDepositException("amount must be greater than 10000");
            }
            try {
                System.out.println(entity.getToken());
                NetworkUtils.decreaseBalance(entity.getToken(), entity.getAmount());
                System.out.println(entity);
                DepositEntity deposit = depositFactory.convertRequestEntityToDepositEntity(entity, entity.getToken());
                System.out.println(deposit);
                depositService.addDeposit(deposit);
            } catch (InsufficientFundsException e) {
                requestService.updateRequestStatus(entity.getId(), RequestStatus.REJECTED);
            }
        }
    }



}
