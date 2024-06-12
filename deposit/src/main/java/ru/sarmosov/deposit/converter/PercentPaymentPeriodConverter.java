package ru.sarmosov.deposit.converter;


import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import ru.sarmosov.bankstarter.enums.PercentPaymentPeriod;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class PercentPaymentPeriodConverter implements AttributeConverter<PercentPaymentPeriod, Integer> {

    @Override
    public Integer convertToDatabaseColumn(PercentPaymentPeriod period) {
        if (period == null) {
            return null;
        }
        return period.getValue();
    }

    @Override
    public PercentPaymentPeriod convertToEntityAttribute(Integer value) {
        if (value == null) {
            return null;
        }

        return Stream.of(PercentPaymentPeriod.values())
                .filter(p -> p.getValue() == value)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

