package ru.sarmosov.bankstarter.enums;

public enum DepositType {

    WITH_DEPOSIT_AND_WITHDRAWAL,
    WITH_DEPOSIT_NO_WITHDRAWAL,
    NO_DEPOSIT_NO_WITHDRAWAL;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }

}