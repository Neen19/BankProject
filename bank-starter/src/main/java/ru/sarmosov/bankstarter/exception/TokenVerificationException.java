package ru.sarmosov.bankstarter.exception;

public class TokenVerificationException extends RuntimeException {
    public TokenVerificationException(String message) {
        super(message);
    }
}
