package ru.sarmosov.deposit.handler;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.sarmosov.bankstarter.annotation.Logging;
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
import java.util.concurrent.BlockingQueue;

@Service
public class HandledRequestConsumer {

    private final RequestService requestService;
    private final BlockingQueue<RequestEntity> handledQueue;
    private final DepositService depositService;
    private final DepositFactory depositFactory;

    public HandledRequestConsumer(
            @Qualifier("handledQueue") BlockingQueue<RequestEntity> handledQueue,
            RequestService requestService, DepositService depositService,
            DepositFactory depositFactory) {
        this.handledQueue = handledQueue;
        this.requestService = requestService;
        this.depositService = depositService;
        this.depositFactory = depositFactory;
    }


//    @Logging(value = "Попытка получить из очереди обработанный запрос")
    @Scheduled(fixedDelay = 5000)
    public void consume() throws InterruptedException {
        if (!handledQueue.isEmpty()) {
            RequestEntity entity = handledQueue.take();
            if (entity.getAmount().compareTo(new BigDecimal("10000")) < 0) {
                requestService.updateRequestDescription(entity.getId(), "The deposit amount is too small");
                throw new LittleDepositException("amount must be greater than 10000");
            }
            try {
                System.out.println(entity.getToken());
                NetworkUtils.decreaseBalance(entity.getToken(), entity.getAmount());
                DepositEntity deposit = depositFactory.convertRequestEntityToDepositEntity(entity);
                depositService.addDeposit(deposit);
            } catch (InsufficientFundsException e) {
                requestService.updateRequestDescription(entity.getId(), "There are insufficient funds in the account");
                requestService.updateRequestStatus(entity.getId(), RequestStatus.REJECTED);
            }
        }
    }


}
