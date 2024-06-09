package ru.sarmosov.account.service;

import ru.sarmosov.bankstarter.dto.BalanceDTO;
import ru.sarmosov.bankstarter.dto.TotalDTO;

public interface BankAccountInterface {

    BalanceDTO increaseBalance(String token, TotalDTO totalDTO);

    BalanceDTO decreaseBalance(String token, TotalDTO totalDTO);

}
