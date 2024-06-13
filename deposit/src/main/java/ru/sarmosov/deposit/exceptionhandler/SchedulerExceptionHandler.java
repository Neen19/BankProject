package ru.sarmosov.deposit.exceptionhandler;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import ru.sarmosov.bankstarter.annotation.Logging;
import ru.sarmosov.bankstarter.enums.RequestStatus;
import ru.sarmosov.deposit.exception.TimeLimitException;
import ru.sarmosov.deposit.exception.UndefinedException;
import ru.sarmosov.deposit.service.request.RequestService;

@Aspect
@Component
@RequiredArgsConstructor
public class SchedulerExceptionHandler {

    private final RequestService requestService;

    @Logging("Отлов ошибки executorservice")
    @AfterThrowing(pointcut = "execution(* ru.sarmosov.deposit.handler.FutureTaskHandler.handledRequest(..))", throwing = "ex")
    public void handleHandledRequestException(Throwable ex) throws UndefinedException {

        if (ex instanceof TimeLimitException) {
            Long id = Long.parseLong(ex.getMessage());
            requestService.updateRequestStatus(id, RequestStatus.REJECTED);
        } else throw new UndefinedException("somethig went wrong");

    }


}
