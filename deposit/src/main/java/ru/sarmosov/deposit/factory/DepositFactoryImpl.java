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
import ru.sarmosov.deposit.entity.RequestEntity;
import ru.sarmosov.bankstarter.enums.DepositType;
import ru.sarmosov.bankstarter.enums.PercentPaymentType;
import ru.sarmosov.deposit.exception.ConstructorException;
import ru.sarmosov.deposit.service.deposit.DepositTypeService;
import ru.sarmosov.deposit.service.deposit.PercentPaymentPeriodService;
import ru.sarmosov.deposit.util.ReflectionUtil;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Component
@RequiredArgsConstructor
public class DepositFactoryImpl implements DepositFactory {

    private final DepositTypeService typeService;
    private final PercentPaymentPeriodService periodService;


    @Override
    public DepositEntity convertRequestEntityToDepositEntity(RequestEntity request) throws ConstructorException {
        return new DepositEntity (
                typeService.getPersistenceEntity(request.getDepositType()),
                request.getAmount(),
                request.getRequestDate(),
                calculateEndDate(request),
                request.getPercent(),
                periodService.getPersistenceEntity(request.getPeriod()),
                request.isCapitalization(),
                request.isMonthly(),
                request.getToken(),
                request.getCustomerId()
        );
    }

    private LocalDate calculateEndDate(RequestEntity request) {
        return request.getRequestDate().plusMonths(request.getPeriod().getValue());
    }

    @Override
    public List<AbstractDeposit> getDeposits(Iterable<DepositEntity> deposits) {
        List<AbstractDeposit> result = new ArrayList<>();
        for (DepositEntity deposit : deposits) {
            result.add(convertDepositEntityToAbstractDeposit(deposit));
        }
        return result;
    }

    private PercentPaymentType getPercentPaymentType(DepositEntity deposit) {
        if (deposit.isMonthly()) return PercentPaymentType.MONTHLY;
        return PercentPaymentType.AT_THE_END;
    }


    @Override
    public AbstractDeposit convertDepositEntityToAbstractDeposit(DepositEntity depositEntity) {
        AbstractDeposit deposit;

        DepositType depositType = depositEntity.getDepositType().getTypeName();

        if (depositEntity.isCapitalization()) {

            if (depositType.equals(DepositType.WITH_DEPOSIT_AND_WITHDRAWAL)) {
                deposit = new DepositableWithdrawableCapitalizationDeposit(
                        depositEntity.getBalance(),
                        depositEntity.getPercent(),
                        depositEntity.getPercentPaymentDate(),
                        depositEntity.getPeriodEntity().getPeriod(),
                        depositEntity.getStartDate(),
                        depositEntity.getEndDate()
                );
            } else if (depositType.equals(DepositType.WITH_DEPOSIT_NO_WITHDRAWAL)) {
                deposit = new DepositableCapitalizationDeposit(
                        depositEntity.getBalance(),
                        depositEntity.getPercent(),
                        depositEntity.getPercentPaymentDate(),
                        depositEntity.getPeriodEntity().getPeriod(),
                        depositEntity.getStartDate(),
                        depositEntity.getEndDate()
                );
            } else {
                deposit = new CapitalizationDeposit(
                        depositEntity.getBalance(),
                        depositEntity.getPercent(),
                        depositEntity.getPercentPaymentDate(),
                        depositEntity.getPeriodEntity().getPeriod(),
                        depositEntity.getStartDate(),
                        depositEntity.getEndDate()
                );
            }

        } else {
            PercentPaymentType type = getPercentPaymentType(depositEntity);
            if (type.equals(PercentPaymentType.AT_THE_END)) {

                if (depositType.equals(DepositType.WITH_DEPOSIT_AND_WITHDRAWAL)) {
                    deposit = new AtTheEndDepositableWithdrawablePercentDeposit(
                            depositEntity.getBalance(),
                            depositEntity.getPercent(),
                            depositEntity.getPercentPaymentDate(),
                            depositEntity.getPeriodEntity().getPeriod(),
                            depositEntity.getStartDate(),
                            depositEntity.getEndDate(),
                            depositEntity.getToken()
                    );
                } else if (depositType.equals(DepositType.WITH_DEPOSIT_NO_WITHDRAWAL)) {
                    deposit = new AtTheEndDepositablePercentDeposit(
                            depositEntity.getBalance(),
                            depositEntity.getPercent(),
                            depositEntity.getPercentPaymentDate(),
                            depositEntity.getPeriodEntity().getPeriod(),
                            depositEntity.getStartDate(),
                            depositEntity.getEndDate(),
                            depositEntity.getToken()
                    );
                } else {
                    deposit = new AtTheEndPercentDeposit(
                            depositEntity.getBalance(),
                            depositEntity.getPercent(),
                            depositEntity.getPercentPaymentDate(),
                            depositEntity.getPeriodEntity().getPeriod(),
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
                            depositEntity.getPeriodEntity().getPeriod(),
                            depositEntity.getStartDate(),
                            depositEntity.getEndDate(),
                            depositEntity.getToken()
                    );
                } else if (depositType.equals(DepositType.WITH_DEPOSIT_NO_WITHDRAWAL)) {
                    deposit = new MonthlyDepositablePercentDeposit(
                            depositEntity.getBalance(),
                            depositEntity.getPercent(),
                            depositEntity.getPercentPaymentDate(),
                            depositEntity.getPeriodEntity().getPeriod(),
                            depositEntity.getStartDate(),
                            depositEntity.getEndDate(),
                            depositEntity.getToken()
                    );
                } else {
                    deposit = new MonthlyPercentDeposit(
                            depositEntity.getBalance(),
                            depositEntity.getPercent(),
                            depositEntity.getPercentPaymentDate(),
                            depositEntity.getPeriodEntity().getPeriod(),
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
