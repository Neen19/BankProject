package ru.sarmosov.customerservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthDTO {

    private String phoneNumber;

    private String password;

}
