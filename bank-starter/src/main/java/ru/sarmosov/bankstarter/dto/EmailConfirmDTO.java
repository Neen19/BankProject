package ru.sarmosov.bankstarter.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class EmailConfirmDTO {

    private Long requestId;

    private int code;
}
