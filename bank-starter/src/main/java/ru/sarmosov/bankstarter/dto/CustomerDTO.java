package ru.sarmosov.bankstarter.dto;


import jakarta.validation.constraints.Email;
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
public class CustomerDTO {

    @NotNull(message = "id can't be null")
    @Positive(message = "id must be positive")
    private Long id;

    @NotNull(message = "id can't be null")
    @Positive(message = "id must be positive")
    private Long bankAccountId;

    @NotBlank(message = "email can't be blank")
    @Email(message = "not valid email")
    private String email;


}