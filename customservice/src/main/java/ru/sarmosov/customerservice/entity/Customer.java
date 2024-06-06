package ru.sarmosov.customerservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "customers")
@EqualsAndHashCode
public class Customer {

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
