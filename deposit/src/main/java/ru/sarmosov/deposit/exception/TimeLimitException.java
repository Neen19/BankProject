package ru.sarmosov.deposit.exception;

import java.util.concurrent.TimeoutException;

public class TimeLimitException extends TimeoutException {

    public TimeLimitException() {
    }

    public TimeLimitException(String message) {
        super(message);
    }
}
