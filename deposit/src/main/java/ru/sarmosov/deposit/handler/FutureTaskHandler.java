package ru.sarmosov.deposit.handler;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.aspectj.weaver.ast.Call;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.util.Pair;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.sarmosov.bankstarter.annotation.Logging;
import ru.sarmosov.deposit.entity.RequestEntity;
import ru.sarmosov.deposit.exception.TimeLimitException;
import ru.sarmosov.deposit.service.request.RequestService;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.*;

@Component
public class FutureTaskHandler {

    private final ExecutorService executor;
    private final BlockingQueue<Pair<RequestEntity, Future<RequestEntity>>> futureQueue;
    private final BlockingQueue<RequestEntity> handledQueue;
    private final RequestService requestService;

    public FutureTaskHandler(
            @Qualifier("futureService") ExecutorService executor,
            @Qualifier("handledQueue") BlockingQueue<RequestEntity> handledQueue,
            @Qualifier("futureQueue") BlockingQueue<Pair<RequestEntity, Future<RequestEntity>>> futureQueue,
            RequestService requestService) {
        this.executor = executor;
        this.futureQueue = futureQueue;
        this.handledQueue = handledQueue;
        this.requestService = requestService;
    }

//    @Logging(value = "Обработка ошибок")
    @Scheduled(fixedDelay = 4000)
    public void handledRequest() throws Throwable {

        if (!futureQueue.isEmpty()) {
            var pair = futureQueue.take();
            RequestEntity request = pair.getFirst();
            Future<RequestEntity> future = pair.getSecond();
            Future<Optional<RequestEntity>> futureReq = executor.submit(new FutureTask(future));
            try {
                Optional<RequestEntity> handled = futureReq.get(1, TimeUnit.MINUTES);
                handled.ifPresent(handledQueue::add);
            } catch (Exception e) {
                requestService.updateRequestDescription(request.getId(), "you haven't verified the code");
                throw new TimeLimitException(request.getId().toString());
            }
        }
    }


    private final class FutureTask implements Callable<Optional<RequestEntity>> {

        private final Future<RequestEntity> future;

        public FutureTask(Future<RequestEntity> future) {
            this.future = future;
        }

        @Override
        public Optional<RequestEntity> call() throws Exception {
            RequestEntity request = null;
            try {
                request = future.get(1, TimeUnit.MINUTES);
            } catch (TimeoutException e) {
                future.cancel(true);
                throw e;
            }
            return Optional.ofNullable(request);
        }

    }

}
