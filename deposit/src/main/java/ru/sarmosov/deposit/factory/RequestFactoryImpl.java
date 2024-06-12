package ru.sarmosov.deposit.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sarmosov.bankstarter.dto.RequestDTO;
import ru.sarmosov.bankstarter.enums.DepositType;
import ru.sarmosov.bankstarter.enums.RequestStatus;
import ru.sarmosov.bankstarter.util.JWTUtil;
import ru.sarmosov.deposit.entity.RequestEntity;
import ru.sarmosov.deposit.service.deposit.RequestStatusService;

@Service
@RequiredArgsConstructor
public class RequestFactoryImpl implements RequestFactory {

    private final JWTUtil jwtUtil;
    private final RequestStatusService statusService;


    @Override
    public RequestEntity convertRequestDTOToEntity(RequestDTO dto, String token) {
        String email = jwtUtil.verifyTokenAndRetrievePhoneNumber(token).getEmail();
        return new RequestEntity(
            dto.getAmount(),
                statusService.getPersistenceEntity(RequestStatus.PENDING_CONFIRMATION),
                token,
                convertRequestDTOToDepositType(dto),
                dto.getPercent(),
                dto.getPaymentPeriod(),
                email
        );
    }

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
