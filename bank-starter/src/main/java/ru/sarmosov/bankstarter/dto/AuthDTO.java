package ru.sarmosov.bankstarter.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthDTO {

    @NotBlank(message = "email can't be blank")
    @Email(message = "not valid email")
    private String email;

    @NotBlank(message = "password can`t be blank")
    private String password;

}
