package ru.sarmosov.deposit.exception;

public class IllegalCodeException extends IllegalArgumentException {

    public IllegalCodeException() {
    }

    public IllegalCodeException(String s) {
        super(s);
    }

    public IllegalCodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalCodeException(Throwable cause) {
        super(cause);
    }
}
