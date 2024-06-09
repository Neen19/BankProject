package ru.sarmosov.deposit.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.sarmosov.deposit.repository.DepositRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class DepositServiceImpl implements DepositService {

    private final DepositRepository depositRepository;
    private final DateTimeFormatter dateTimeFormatter;


    @Scheduled(cron = "25 25 18 * * ?",  zone = "Europe/Moscow")
    public void payPercent() {
        LocalDate now = LocalDate.now();

    }


}
