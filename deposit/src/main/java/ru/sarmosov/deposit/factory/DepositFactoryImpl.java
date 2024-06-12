package ru.sarmosov.deposit.factory;

import lombok.RequiredArgsConstructor;
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
import ru.sarmosov.deposit.entity.RequestEntity;
import ru.sarmosov.bankstarter.enums.DepositType;
import ru.sarmosov.bankstarter.enums.PercentPaymentType;
import ru.sarmosov.deposit.repository.DepositTypeRepository;
import ru.sarmosov.deposit.repository.PercentPaymentPeriodRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



@RequiredArgsConstructor
public class DepositFactoryImpl implements DepositFactory {

    private final PercentPaymentPeriodRepository paymentTypeRepository;
    private final DepositTypeRepository depositTypeRepository;


    @Override
    public DepositEntity convertRequestEntityToDepositEntity(RequestEntity request, String token) {


        DepositEntity depositEntity = new DepositEntity (
            request.getAccountId(),
                request.getDepositType().getId(),
                request.isRefillable(),
                request.getAmount(),
                LocalDate.now(),
                LocalDate.now().plusMonths(3),
                request.getPercent(),
                1,
                LocalDate.now().plusMonths(1),
                request.getCustomerId(),
                false,
                request.isWithdrawal(),
                request.getCustomerId(),
                token
        );
        return depositEntity;
    }

    @Override
    public List<AbstractDeposit> getDeposits(Iterable<DepositEntity> deposits) {
        List<AbstractDeposit> result = new ArrayList<>();
        for (DepositEntity deposit : deposits) {
            result.add(convertDepositEntityToAbstractDeposit(deposit));
        }
        return result;
    }


    @Override
    public AbstractDeposit convertDepositEntityToAbstractDeposit(DepositEntity depositEntity) {
        AbstractDeposit deposit;

        DepositType depositType = depositTypeRepository.findById(depositEntity.getTypeId()).orElseThrow().getTypeName();

        if (depositEntity.isCapitalization()) {

            if (depositType.equals(DepositType.WITH_DEPOSIT_AND_WITHDRAWAL)) {
                deposit = new DepositableWithdrawableCapitalizationDeposit(
                        depositEntity.getBalance(),
                        depositEntity.getPercent(),
                        depositEntity.getPercentPaymentDate(),
                        paymentTypeRepository.findById(depositEntity.getPeriodPercentPaymentId()).orElseThrow().getPeriod(),
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
