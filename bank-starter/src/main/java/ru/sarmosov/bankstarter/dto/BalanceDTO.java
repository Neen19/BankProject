package ru.sarmosov.bankstarter.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BalanceDTO {
    BigDecimal balance;
}
