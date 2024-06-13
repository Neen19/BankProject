package ru.sarmosov.deposit.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sarmosov.bankstarter.annotation.Logging;
import ru.sarmosov.bankstarter.dto.RequestDTO;
import ru.sarmosov.bankstarter.enums.DepositType;
import ru.sarmosov.bankstarter.enums.RequestStatus;
import ru.sarmosov.bankstarter.util.JWTUtil;
import ru.sarmosov.deposit.entity.RequestEntity;
import ru.sarmosov.deposit.exception.ConstructorException;
import ru.sarmosov.deposit.service.deposit.RequestStatusService;
import ru.sarmosov.deposit.service.request.RequestService;
import ru.sarmosov.deposit.util.ReflectionUtil;

@Service
@RequiredArgsConstructor
public class RequestFactoryImpl implements RequestFactory {

    private final JWTUtil jwtUtil;
    private final RequestStatusService statusService;
    private final RequestService requestService;


    @Logging(value = "Конвертация DTO запроса в БД запрос")
    @Override
    public RequestEntity convertRequestDTOToEntity(RequestDTO dto, String token) throws ConstructorException {
        Long customerId = jwtUtil.verifyTokenAndRetrievePhoneNumber(token).getId();
        System.out.println(dto.isCapitalization());
        RequestEntity entity = new RequestEntity(
            dto.getAmount(),
                statusService.getPersistenceEntity(RequestStatus.PENDING_CONFIRMATION),
                token,
                convertRequestDTOToDepositType(dto),
                dto.getPercent(),
                dto.getPaymentPeriod(),
                customerId,
                dto.getPaymentType(),
                dto.isCapitalization(),
                "request received"
        );
        if (!ReflectionUtil.isFieldsNotNull(entity)) throw new ConstructorException("json mapping exception");
        requestService.addRequest(entity);
        return entity;
    }

    @Logging(value = "Получение типа вклада из DTO зпроса")
    private DepositType convertRequestDTOToDepositType(RequestDTO dto) {
        boolean isRefillable = dto.isRefillable();
        boolean isWithdrawal = dto.isWithdrawal();
        if (isRefillable) {
            if (isWithdrawal) {
                return DepositType.WITH_DEPOSIT_AND_WITHDRAWAL;
            } else return DepositType.WITH_DEPOSIT_NO_WITHDRAWAL;
        } else return DepositType.NO_DEPOSIT_NO_WITHDRAWAL;
    }

}
