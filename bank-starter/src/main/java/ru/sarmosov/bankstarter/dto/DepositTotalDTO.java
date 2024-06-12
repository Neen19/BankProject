package ru.sarmosov.bankstarter.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class DepositTotalDTO {

    @NotNull(message = "id can't be blank")
    @Positive(message = "id must be positive")
    private Long id;

    @PositiveOrZero(message = "balance can't be negative")
    @NotNull(message = "balance can't be blank")
    private BigDecimal amount;

}
