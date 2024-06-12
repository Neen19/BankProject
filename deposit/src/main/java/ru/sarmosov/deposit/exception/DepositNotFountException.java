package ru.sarmosov.deposit.exception;

public class DepositNotFountException extends Exception{
    public DepositNotFountException() {
    }

    public DepositNotFountException(String message) {
        super(message);
    }

    public DepositNotFountException(String message, Throwable cause) {
        super(message, cause);
    }

    public DepositNotFountException(Throwable cause) {
        super(cause);
    }
}
