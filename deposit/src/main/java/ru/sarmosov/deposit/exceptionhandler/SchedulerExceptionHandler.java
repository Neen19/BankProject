package ru.sarmosov.deposit.exceptionhandler;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import ru.sarmosov.bankstarter.enums.RequestStatus;
import ru.sarmosov.deposit.exception.TimeLimitException;
import ru.sarmosov.deposit.service.request.RequestService;

@Aspect
@Component
@RequiredArgsConstructor
public class SchedulerExceptionHandler {

    private final RequestService requestService;

//    @AfterThrowing(pointcut = "execution(* ru.sarmosov.deposit.service.handler.FutureTaskHandler.handledRequest(..))", throwing = "ex")
//    public void handleHandledRequestException(Throwable ex) {
//        Long id = Long.parseLong(ex.getMessage());
//        requestService.updateRequestStatus(id, RequestStatus.REJECTED);
//    }

//    public void handleScheduledTaskException(TimeLimitException ex) {
//        Long id = Long.parseLong(ex.getMessage());
//        requestService.updateRequestStatus(id, RequestStatus.REJECTED);
//    }

}
