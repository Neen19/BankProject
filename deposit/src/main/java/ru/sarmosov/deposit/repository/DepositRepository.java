package ru.sarmosov.deposit.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.sarmosov.deposit.entity.DepositEntity;
import ru.sarmosov.bankstarter.enums.PercentPaymentType;

import java.util.List;

@Repository
public interface DepositRepository extends CrudRepository<DepositEntity, Long> {
    @Query("SELECT d FROM DepositEntity d WHERE d.isCapitalization = true or d.typePercentPaymentId = " +
            "(SELECT p.id FROM PercentPaymentPeriodEntity p WHERE p.percentPaymentType = :percentPaymentType)")

    List<DepositEntity> findDepositsByTypeAndMinPercent(
            @Param("percentPaymentType") PercentPaymentType typeId
    );
}
