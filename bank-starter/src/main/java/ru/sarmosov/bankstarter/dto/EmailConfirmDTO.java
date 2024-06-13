package ru.sarmosov.bankstarter.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class EmailConfirmDTO {

    @NotNull(message = "id can't be blank")
    @Positive(message = "id must be positive")
    private Long requestId;

    @NotNull(message = "code can't be blank")
    @Positive(message = "code must be positive")
    private int code;
}
