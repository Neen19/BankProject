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
public class IdDTO {

    @NotNull(message = "id must be positive")
    @Positive(message = "id must be positive")
    private Long id;
}
