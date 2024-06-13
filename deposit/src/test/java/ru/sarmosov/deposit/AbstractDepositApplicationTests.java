package ru.sarmosov.deposit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import ru.sarmosov.bankstarter.dto.RequestDTO;
import ru.sarmosov.bankstarter.enums.PercentPaymentType;
import ru.sarmosov.deposit.dto.DepositDTO;
import ru.sarmosov.bankstarter.enums.DepositType;
import ru.sarmosov.bankstarter.enums.PercentPaymentPeriod;
import ru.sarmosov.deposit.entity.DepositEntity;
import ru.sarmosov.deposit.entity.DepositTypeEntity;
import ru.sarmosov.deposit.entity.PercentPaymentPeriodEntity;
import ru.sarmosov.deposit.util.NetworkUtils;

import java.math.BigDecimal;
import java.time.LocalDate;

class AbstractDepositApplicationTests {


    public static void main(String[] args) throws JsonProcessingException {
//        ModelMapper modelMapper = new ModelMapper();
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule());
//
//        String jsonString = "{"
//                + "\"amount\":12000,"
//                + "\"percent\":12,"
//                + "\"paymentType\":\"AT_THE_END\","
//                + "\"paymentPeriod\":\"THREE_MONTHS\","
//                + "\"withdrawal\":false,"
//                + "\"refillable\":true,"
//                + "\"capitalization\":true"
//                + "}";
//
//        System.out.println(objectMapper.readValue(jsonString, RequestDTO.class));

        String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjdXN0b21lciIsImJhbmstaWQiOjEsImlzcyI6ImFkbWluIiwiaWQiOjEsImlhdCI6MTcxODI3NjAxMCwiZW1haWwiOiJmYWtlZmFrZW4xMDExQGdtYWlsLmNvbSJ9.yaY8mmMjOomAm7GlgcNNNzfnZMr0w-I6H6Y1EwgCaWQ";

        NetworkUtils.decreaseBalance(token, new BigDecimal(100));

    }
}
