package ru.sarmosov.customerservice.dto;

public class ErrorResponse {

    private String error;

    public ErrorResponse(String error) {
        this.error = error;
    }

    public ErrorResponse() {
    }
}
