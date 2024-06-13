package ru.sarmosov.deposit.service.deposit;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sarmosov.bankstarter.annotation.Logging;
import ru.sarmosov.bankstarter.enums.DepositType;
import ru.sarmosov.deposit.entity.DepositTypeEntity;
import ru.sarmosov.deposit.repository.DepositTypeRepository;

@Service
@RequiredArgsConstructor
public class DepositTypeServiceImpl implements DepositTypeService {

    private final DepositTypeRepository repository;

    @Logging
    public DepositTypeEntity getPersistenceEntity(DepositType depositType) {
        return repository.findByTypeName(depositType).orElseThrow();
    }

}
