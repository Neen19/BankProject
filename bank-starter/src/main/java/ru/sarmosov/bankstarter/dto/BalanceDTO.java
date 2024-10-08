package ru.sarmosov.bankstarter.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class BalanceDTO {

    @PositiveOrZero(message = "balance can't be negative")
    @NotNull(message = "balance can't be blank")
    BigDecimal balance;


}
