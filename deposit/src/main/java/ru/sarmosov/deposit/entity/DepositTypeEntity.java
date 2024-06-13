package ru.sarmosov.deposit.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.sarmosov.bankstarter.enums.DepositType;

import java.util.Set;


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

    @JsonIgnore
    @OneToMany(mappedBy = "id")
    private Set<DepositEntity> deposits;

    public DepositTypeEntity(DepositType typeName) {
        this.typeName = typeName;
    }


}
