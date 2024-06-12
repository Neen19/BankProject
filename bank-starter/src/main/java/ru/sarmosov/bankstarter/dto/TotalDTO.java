package ru.sarmosov.bankstarter.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class TotalDTO {

    @NotNull(message = "can't be blank")
    @PositiveOrZero(message = "can't be negative")
    private BigDecimal total;

}
