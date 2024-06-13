package ru.sarmosov.deposit.handler;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.util.Pair;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.sarmosov.bankstarter.util.JWTUtil;
import ru.sarmosov.deposit.entity.RequestEntity;
import ru.sarmosov.bankstarter.enums.RequestStatus;
import ru.sarmosov.deposit.exception.IllegalCodeException;
import ru.sarmosov.deposit.exception.TimeLimitException;
import ru.sarmosov.deposit.service.mail.MailService;
import ru.sarmosov.deposit.service.request.RequestService;
import ru.sarmosov.deposit.util.RandomUtil;
import org.springframework.data.util.Pair;

import java.util.Optional;
import java.util.concurrent.*;


@Service
public class RequestHandler {

    private final ExecutorService executorService;
    private final RequestService requestService;
    private final BlockingQueue<RequestEntity> taskQueue;
    private final BlockingQueue<Pair<RequestEntity, Future<RequestEntity>>> futureQueue;
    private final ConcurrentHashMap<Long, Integer> codeMap;
    private final MailService mailService;
    private final JWTUtil jwtUtil;


    @Autowired
    public RequestHandler(
            @Qualifier("taskService") ExecutorService executorService,
            RequestService requestService,
            @Qualifier("taskQueue") BlockingQueue<RequestEntity> taskQueue,
            @Qualifier("futureQueue") BlockingQueue<Pair<RequestEntity, Future<RequestEntity>>> futureQueue,
            ConcurrentHashMap<Long, Integer> codeMap,
            MailService mailService,
            JWTUtil jwtUtil) {

        this.executorService = executorService;
        this.requestService = requestService;
        this.taskQueue = taskQueue;
        this.futureQueue = futureQueue;
        this.codeMap = codeMap;
        this.mailService = mailService;
        this.jwtUtil = jwtUtil;
    }


    public void addRequest(RequestEntity request) {
        taskQueue.add(request);
    }


    @Scheduled(fixedDelay = 4000)
    public void executeTask() throws Throwable {
        try {
            if (!taskQueue.isEmpty()) {
                RequestEntity request = taskQueue.take();
                String email = jwtUtil.verifyTokenAndRetrievePhoneNumber(request.getToken()).getEmail();
                requestService.updateRequestDescription(request.getId(), "Sending verification code on your email");
                Future<RequestEntity> future = executorService.submit(new RequestTask(request, email));
                futureQueue.add(Pair.of(request, future));
            }
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }

    }


    private final class RequestTask implements Callable<RequestEntity> {

        private final RequestEntity request;
        private final String email;

        public RequestTask(RequestEntity request, String email) {
            this.request = request;
            this.email = email;
        }

        @Override
        public RequestEntity call() throws IllegalCodeException, InterruptedException {
            int code = RandomUtil.getRandomCode();
            mailService.sendMail(
                    email,
                    "Verification code",
                    "Your verification code is " + code +"\n" +
                            "Your request id is" + request.getId()
            );
            requestService.updateRequestDescription(request.getId(), "Verification code send");
            while (!codeMap.containsKey(request.getId())) {
                System.out.println("contains key" + request.getId() + codeMap.containsKey(request.getId()));
                Thread.sleep(10000);
            }
            int enteredCode = codeMap.get(request.getId());
            if (code != enteredCode)
                throw new IllegalCodeException(request.getId().toString());
            return requestService.updateRequestStatus(request.getId(), RequestStatus.CONFIRMED);
        }

    }


}
