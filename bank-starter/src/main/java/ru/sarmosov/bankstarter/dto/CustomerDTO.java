package ru.sarmosov.bankstarter.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CustomerDTO {

    private Long id;

    private Long bankAccountId;

    private String phoneNumber;

    @Override
    public String toString() {
        return "CustomerDTO{" +
                "id=" + id +
                ", bankAccountId=" + bankAccountId +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}