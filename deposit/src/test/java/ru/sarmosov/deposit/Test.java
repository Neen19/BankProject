package ru.sarmosov.deposit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.stereotype.Repository;
import ru.sarmosov.bankstarter.dto.RequestDTO;
import ru.sarmosov.bankstarter.enums.PercentPaymentType;
import ru.sarmosov.deposit.util.NetworkUtils;

import java.math.BigDecimal;


public class Test {
    ObjectMapper mapper = new ObjectMapper();

    @org.junit.jupiter.api.Test
    void init() {
        System.out.println(PercentPaymentType.AT_THE_END.name());
    }



    @org.junit.jupiter.api.Test
    void test() throws JsonProcessingException {

        String json = "{\"amount\":100,\"depositType\":\"NO_DEPOSIT_NO_WITHDRAWAL\",\"endDate\":[2024,9,12],\"percent\":10,\"paymentType\":\"AT_THE_END\",\"paymentPeriod\":\"THREE_MONTHS\",\"refillable\":false,\"withdrawal\":false}";

        RequestDTO dto = mapper.readValue(json, RequestDTO.class);


    }



}
