package ru.sarmosov.deposit.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.sarmosov.bankstarter.enums.RequestStatus;

import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "request_statuses")
public class RequestStatusEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_request_status")
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name = "request_status_name")
    private RequestStatus status;

    @OneToMany(mappedBy = "id")
    private Set<RequestEntity> requestEntitySet;

    public RequestStatusEntity(RequestStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "RequestStatusEntity{" +
                "id=" + id +
                '}';
    }
}
