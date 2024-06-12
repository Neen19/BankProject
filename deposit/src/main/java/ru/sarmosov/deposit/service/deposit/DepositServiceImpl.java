package ru.sarmosov.deposit.service.deposit;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sarmosov.bankstarter.dto.DepositTotalDTO;
import ru.sarmosov.bankstarter.exception.InsufficientFundsException;
import ru.sarmosov.deposit.entity.DepositEntity;
import ru.sarmosov.deposit.exception.DepositNotFountException;
import ru.sarmosov.deposit.repository.DepositRepository;
import ru.sarmosov.deposit.util.NetworkUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepositServiceImpl implements DepositService {

    private final DepositRepository depositRepository;

    @Override
    @Transactional
    public void refreshBalance(DepositEntity deposit, BigDecimal balance) {
        deposit.setBalance(balance);
        depositRepository.save(deposit);
    }

    @Override
    @Transactional
    public DepositEntity addDeposit(DepositEntity deposit) {
        return depositRepository.save(deposit);
    }

    @Override
    @Transactional
    public DepositEntity shutDownDeposit(Long depositId) {
        DepositEntity deposit = depositRepository.findById(depositId).orElseThrow();
        deposit.setEndDate(LocalDate.now());
        return depositRepository.save(deposit);
    }

    @Transactional
    public DepositTotalDTO increaseBalance(DepositTotalDTO depositTotalDTO, String token) throws DepositNotFountException {
        List<DepositEntity> deposits = depositRepository.findWithdrawal();
        DepositEntity deposit = findDepositInList(depositTotalDTO.getId(), deposits);

        NetworkUtils.decreaseBalance(token, depositTotalDTO.getAmount());

        deposit.setBalance(deposit.getBalance().add(depositTotalDTO.getAmount()));
        DepositEntity entity = depositRepository.save(deposit);
        return new DepositTotalDTO(depositTotalDTO.getId(), entity.getBalance());
    }

    @Transactional
    public DepositTotalDTO decreaseBalance(DepositTotalDTO depositTotalDTO, String token) throws DepositNotFountException {
        List<DepositEntity> deposits = depositRepository.findWithdrawal();
        DepositEntity deposit = findDepositInList(depositTotalDTO.getId(), deposits);

        NetworkUtils.increaseBalance(token, depositTotalDTO.getAmount());

        deposit.setBalance(deposit.getBalance().subtract(depositTotalDTO.getAmount()));
        DepositEntity entity = depositRepository.save(deposit);
        return new DepositTotalDTO(depositTotalDTO.getId(), entity.getBalance());
    }

    private DepositEntity findDepositInList(Long id, List<DepositEntity> list) throws DepositNotFountException {
        DepositEntity deposit;
        Optional<DepositEntity> depositOptional = list.stream()
                .filter(it -> it.getId().equals(id))
                .findFirst();
        if (depositOptional.isPresent()) {
            deposit = depositOptional.get();
        } else throw new DepositNotFountException("deposit with id " + id + " not found");
        return deposit;
    }
}
