package ru.sarmosov.customerservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@Entity(name = "customers")
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_customers")
    private Long id;

    @Column(name = "bank_account_id")
    private Long bankAccountId;

    @Column(name = "customer_email")
    private String email;

    @Column(name = "password")
    private String password;

    public CustomerEntity(Long bankAccountId, String email, String password) {
        this.bankAccountId = bankAccountId;
        this.email = email;
        this.password = password;
    }
}
