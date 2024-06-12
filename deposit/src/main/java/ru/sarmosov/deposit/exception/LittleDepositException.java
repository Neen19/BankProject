package ru.sarmosov.deposit.exception;

public class LittleDepositException extends IllegalArgumentException{

    public LittleDepositException() {
    }

    public LittleDepositException(String s) {
        super(s);
    }

    public LittleDepositException(String message, Throwable cause) {
        super(message, cause);
    }

    public LittleDepositException(Throwable cause) {
        super(cause);
    }
}
