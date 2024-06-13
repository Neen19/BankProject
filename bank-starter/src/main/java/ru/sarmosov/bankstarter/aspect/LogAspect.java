package ru.sarmosov.bankstarter.aspect;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.spi.LoggerContextFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.sarmosov.bankstarter.annotation.Logging;

import java.util.Optional;

@Aspect
@Component
public class LogAspect {

    private static final String ENTER = ">> {} >>";
    private static final String EXIT = "<< {} <<";


    @Around("@annotation(logging))")
    public Object execute(ProceedingJoinPoint joinPoint, Logging logging) throws Throwable {

        Object[] methodArgs = joinPoint.getArgs();

        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();

        String annotationValue = fetchAnnotationValue(joinPoint, logging);
        Level level = Level.valueOf(logging.level());
        Logger logger = LogManager.getLogger(fetchJoinPointObject(joinPoint));


        if (logging.entering()) logger.log(level, ENTER, className + " " + methodName);
        logger.info("Метод {} получил аргументы: {}", methodName, methodArgs);
        Object result = joinPoint.proceed();
        if (logging.exiting()) logger.log(level, EXIT, annotationValue);

        return result;
    }

    private String fetchAnnotationValue(ProceedingJoinPoint joinPoint, Logging logging) {
        return Optional.ofNullable(logging)
                .map(Logging::value)
                .filter(it -> !it.isBlank())
                .orElse(joinPoint.getSignature().getName());
    }

    private Object fetchJoinPointObject(ProceedingJoinPoint joinPoint) {
        return Optional.of(joinPoint)
                .map(JoinPoint::getTarget)
                .orElseGet(joinPoint::getThis);
    }

}
