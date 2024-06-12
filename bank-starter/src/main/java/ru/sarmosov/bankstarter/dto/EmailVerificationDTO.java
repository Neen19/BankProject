package ru.sarmosov.bankstarter.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class EmailVerificationDTO {
    private int code;
}
