package ru.sarmosov.bankstarter.enums;

import lombok.Getter;

@Getter
public enum PercentPaymentPeriod {

    THREE_MONTHS(3),
    SIX_MONTHS(6),
    ONE_YEAR(12);

    private final int value;

    PercentPaymentPeriod(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName().toLowerCase();
    }



}
