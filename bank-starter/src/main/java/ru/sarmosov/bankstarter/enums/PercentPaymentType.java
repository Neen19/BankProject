package ru.sarmosov.bankstarter.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PercentPaymentType {
    MONTHLY(1), AT_THE_END(2);

    private final int id;

}