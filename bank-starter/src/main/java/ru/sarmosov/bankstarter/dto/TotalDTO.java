package ru.sarmosov.bankstarter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TotalDTO {

    private BigDecimal total;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TotalDTO totalDTO)) return false;
        return Objects.equals(total, totalDTO.total);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(total);
    }
}
