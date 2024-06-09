package ru.sarmosov.bankstarter.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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