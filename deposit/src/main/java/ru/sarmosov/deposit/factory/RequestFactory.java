package ru.sarmosov.deposit.factory;

import ru.sarmosov.bankstarter.dto.RequestDTO;
import ru.sarmosov.deposit.entity.RequestEntity;

public interface RequestFactory {

    RequestEntity convertRequestDTOToEntity(RequestDTO requestDTO, String token);

}
