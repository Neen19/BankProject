package ru.sarmosov.account.service;

import ru.sarmosov.account.dto.TotalDTO;

public interface BankAccountInterface {

    public void increaseBalance(String token, TotalDTO totalDTO);

    public void decreaseBalance(String token, TotalDTO totalDTO);

}
