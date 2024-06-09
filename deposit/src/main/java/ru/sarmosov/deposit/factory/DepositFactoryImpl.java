package ru.sarmosov.deposit.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.sarmosov.deposit.deposit.AbstractDeposit;
import ru.sarmosov.deposit.deposit.capitalization.CapitalizationDeposit;
import ru.sarmosov.deposit.deposit.capitalization.DepositableCapitalizationDeposit;
import ru.sarmosov.deposit.deposit.capitalization.DepositableWithdrawableCapitalizationDeposit;
import ru.sarmosov.deposit.deposit.percent.attheend.AtTheEndDepositablePercentDeposit;
import ru.sarmosov.deposit.deposit.percent.attheend.AtTheEndDepositableWithdrawablePercentDeposit;
import ru.sarmosov.deposit.deposit.percent.attheend.AtTheEndPercentDeposit;
import ru.sarmosov.deposit.deposit.percent.monthly.MonthlyDepositablePercentDeposit;
import ru.sarmosov.deposit.deposit.percent.monthly.MonthlyDepositableWithdrawablePercentDeposit;
import ru.sarmosov.deposit.deposit.percent.monthly.MonthlyPercentDeposit;
import ru.sarmosov.deposit.entity.DepositEntity;
import ru.sarmosov.deposit.enums.DepositType;
import ru.sarmosov.deposit.enums.PercentPaymentType;
import ru.sarmosov.deposit.repository.DepositTypeRepository;
import ru.sarmosov.deposit.repository.PercentPaymentTypeRepository;

import java.util.ArrayList;
import java.util.List;


@Component
@RequiredArgsConstructor
public class DepositFactoryImpl implements DepositFactory {

    public List<AbstractDeposit> getDeposits(List<DepositEntity> deposits) {
        List<AbstractDeposit> result = new ArrayList<>();
        for (DepositEntity deposit : deposits) {
            result.add(convertDepositEntityToAbstractDeposit(deposit));
        }
        return result;
    }

    private final PercentPaymentTypeRepository paymentTypeRepository;
    private final DepositTypeRepository depositTypeRepository;


    private AbstractDeposit convertDepositEntityToAbstractDeposit(DepositEntity depositEntity) {
        AbstractDeposit deposit;

        DepositType depositType = depositTypeRepository.findById(depositEntity.getTypeId()).orElseThrow().getTypeName();

        if (depositEntity.isCapitalization()) {

            if (depositType.equals(DepositType.WITH_DEPOSIT_AND_WITHDRAWAL)) {
                deposit = new DepositableWithdrawableCapitalizationDeposit(
                        depositEntity.getBalance(),
                        depositEntity.getPercent(),
                        depositEntity.getPercentPaymentDate(),
                        paymentTypeRepository.findById(depositEntity.getTypePercentPaymentId()).orElseThrow().getPeriod(),
                        depositEntity.getStartDate(),
                        depositEntity.getEndDate()
                );
            } else if (depositType.equals(DepositType.WITH_DEPOSIT_NO_WITHDRAWAL)) {
                deposit = new DepositableCapitalizationDeposit(
                        depositEntity.getBalance(),
                        depositEntity.getPercent(),
                        depositEntity.getPercentPaymentDate(),
                        paymentTypeRepository.findById(depositEntity.getTypePercentPaymentId()).orElseThrow().getPeriod(),
                        depositEntity.getStartDate(),
                        depositEntity.getEndDate()
                );
            } else {
                deposit = new CapitalizationDeposit(
                        depositEntity.getBalance(),
                        depositEntity.getPercent(),
                        depositEntity.getPercentPaymentDate(),
                        paymentTypeRepository.findById(depositEntity.getTypePercentPaymentId()).orElseThrow().getPeriod(),
                        depositEntity.getStartDate(),
                        depositEntity.getEndDate()
                );
            }

        } else {
            PercentPaymentType type = paymentTypeRepository.findById(depositEntity.getTypePercentPaymentId()).orElseThrow().getPercentPaymentType();
            if (type.equals(PercentPaymentType.AT_THE_END)) {

                if (depositType.equals(DepositType.WITH_DEPOSIT_AND_WITHDRAWAL)) {
                    deposit = new AtTheEndDepositableWithdrawablePercentDeposit(
                            depositEntity.getBalance(),
                            depositEntity.getPercent(),
                            depositEntity.getPercentPaymentDate(),
                            paymentTypeRepository.findById(depositEntity.getTypePercentPaymentId()).orElseThrow().getPeriod(),
                            depositEntity.getStartDate(),
                            depositEntity.getEndDate(),
                            depositEntity.getToken()
                    );
                } else if (depositType.equals(DepositType.WITH_DEPOSIT_NO_WITHDRAWAL)) {
                    deposit = new AtTheEndDepositablePercentDeposit(
                            depositEntity.getBalance(),
                            depositEntity.getPercent(),
                            depositEntity.getPercentPaymentDate(),
                            paymentTypeRepository.findById(depositEntity.getTypePercentPaymentId()).orElseThrow().getPeriod(),
                            depositEntity.getStartDate(),
                            depositEntity.getEndDate(),
                            depositEntity.getToken()
                    );
                } else {
                    deposit = new AtTheEndPercentDeposit(
                            depositEntity.getBalance(),
                            depositEntity.getPercent(),
                            depositEntity.getPercentPaymentDate(),
                            paymentTypeRepository.findById(depositEntity.getTypePercentPaymentId()).orElseThrow().getPeriod(),
                            depositEntity.getStartDate(),
                            depositEntity.getEndDate(),
                            depositEntity.getToken()
                    );
                }
            } else {

                if (depositType.equals(DepositType.WITH_DEPOSIT_AND_WITHDRAWAL)) {
                    deposit = new MonthlyDepositableWithdrawablePercentDeposit(
                            depositEntity.getBalance(),
                            depositEntity.getPercent(),
                            depositEntity.getPercentPaymentDate(),
                            paymentTypeRepository.findById(depositEntity.getTypePercentPaymentId()).orElseThrow().getPeriod(),
                            depositEntity.getStartDate(),
                            depositEntity.getEndDate(),
                            depositEntity.getToken()
                    );
                } else if (depositType.equals(DepositType.WITH_DEPOSIT_NO_WITHDRAWAL)) {
                    deposit = new MonthlyDepositablePercentDeposit(
                            depositEntity.getBalance(),
                            depositEntity.getPercent(),
                            depositEntity.getPercentPaymentDate(),
                            paymentTypeRepository.findById(depositEntity.getTypePercentPaymentId()).orElseThrow().getPeriod(),
                            depositEntity.getStartDate(),
                            depositEntity.getEndDate(),
                            depositEntity.getToken()
                    );
                } else {
                    deposit = new MonthlyPercentDeposit(
                            depositEntity.getBalance(),
                            depositEntity.getPercent(),
                            depositEntity.getPercentPaymentDate(),
                            paymentTypeRepository.findById(depositEntity.getTypePercentPaymentId()).orElseThrow().getPeriod(),
                            depositEntity.getStartDate(),
                            depositEntity.getEndDate(),
                            depositEntity.getToken()
                    );
                }
            }
        }
        return deposit;

    }
}
