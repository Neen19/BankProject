package ru.sarmosov.deposit.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.sarmosov.deposit.enums.DepositType;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "deposits_types")
public class DepositTypeEntity {

    @Id
    @Column(name = "id_deposits_types")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "deposits_types_name")
    @Enumerated(EnumType.STRING)
    private DepositType typeName;

    public DepositTypeEntity(DepositType typeName) {
        this.typeName = typeName;
    }
}
