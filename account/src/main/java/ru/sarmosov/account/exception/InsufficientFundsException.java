package ru.sarmosov.account.exception;

public class InsufficientFundsException extends IllegalArgumentException {
    public InsufficientFundsException() {
    }

    public InsufficientFundsException(String s) {
        super(s);
    }

    public InsufficientFundsException(String message, Throwable cause) {
        super(message, cause);
    }

    public InsufficientFundsException(Throwable cause) {
        super(cause);
    }
}
