package ru.sarmosov.bankstarter.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ErrorResponseDTO {

    @NotBlank(message = "error can't be blank")
    String error;

}
