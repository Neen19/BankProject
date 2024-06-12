package ru.sarmosov.customerservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "customers")
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_customers")
    private Long id;

    @Column(name = "bank_account_id")
    private Long bankAccountId;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "password")
    private String password;


}
