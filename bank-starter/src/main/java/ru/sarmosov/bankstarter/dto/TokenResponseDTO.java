package ru.sarmosov.bankstarter.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TokenResponseDTO {

    @NotBlank(message = "token can't be blank")
    private String token;

}
