package ru.sarmosov.bankstarter.enums;

public enum DepositType {

    WITH_DEPOSIT_AND_WITHDRAWAL(1),
    WITH_DEPOSIT_NO_WITHDRAWAL(2),
    NO_DEPOSIT_NO_WITHDRAWAL(3);

    DepositType(int id) {
        this.id = id;
    }

    private final int id;

    public int getId() {
        return id;
    }
}