package ru.sarmosov.account.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;


@Entity
@Table(name = "bank_accounts")
@Getter
@Setter
@NoArgsConstructor
public class BankAccountEntity {

    @Id
    @Column(name = "id_bank_account")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "num_bank_account")
    private Long accountNumber;

    @Column(name = "amount")
    private BigDecimal balance;

    @Transactional
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
