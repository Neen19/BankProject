package ru.sarmosov.deposit.service.deposit;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sarmosov.bankstarter.annotation.Logging;
import ru.sarmosov.bankstarter.dto.DepositTotalDTO;
import ru.sarmosov.bankstarter.exception.InsufficientFundsException;
import ru.sarmosov.deposit.deposit.AbstractDeposit;
import ru.sarmosov.deposit.entity.DepositEntity;
import ru.sarmosov.deposit.exception.DepositNotFountException;
import ru.sarmosov.deposit.exception.EndedDepositException;
import ru.sarmosov.deposit.factory.DepositFactory;
import ru.sarmosov.deposit.factory.DepositFactoryImpl;
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
    private final DepositFactory factory;

    @Logging
    @Override
    @Transactional
    public void refreshBalance(DepositEntity deposit, BigDecimal balance) {
        deposit.setBalance(balance);
        depositRepository.save(deposit);
    }

    @Logging
    @Override
    @Transactional
    public DepositEntity addDeposit(DepositEntity deposit) {
        return depositRepository.save(deposit);
    }

    @Logging
    @Override
    @Transactional
    public DepositEntity shutDownDeposit(Long depositId) {
        DepositEntity deposit = depositRepository.findById(depositId).orElseThrow();
        if (deposit.getEndDate().equals(LocalDate.now())) throw new EndedDepositException();
        AbstractDeposit abstractDeposit = factory.convertDepositEntityToAbstractDeposit(deposit);
        abstractDeposit.shutDown();
        deposit.setEndDate(LocalDate.now());
        return depositRepository.save(deposit);
    }

    @Logging
    @Transactional
    public DepositTotalDTO increaseBalance(DepositTotalDTO depositTotalDTO, String token) throws DepositNotFountException {
        List<DepositEntity> deposits = depositRepository.findWithdrawal();
        DepositEntity deposit = findDepositInList(depositTotalDTO.getId(), deposits);

        NetworkUtils.decreaseBalance(token, depositTotalDTO.getAmount());

        deposit.setBalance(deposit.getBalance().add(depositTotalDTO.getAmount()));
        DepositEntity entity = depositRepository.save(deposit);
        return new DepositTotalDTO(depositTotalDTO.getId(), entity.getBalance());
    }

    @Logging
    @Transactional
    public DepositTotalDTO decreaseBalance(DepositTotalDTO depositTotalDTO, String token) throws DepositNotFountException {
        List<DepositEntity> deposits = depositRepository.findWithdrawal();
        if (deposits.isEmpty()) {
            throw new DepositNotFountException("withdrawal deposits not found");
        }
        DepositEntity deposit = findDepositInList(depositTotalDTO.getId(), deposits);
        if (deposit.getBalance().compareTo(depositTotalDTO.getAmount()) < 0) {
            throw new InsufficientFundsException("insufficient funds");
        }

        NetworkUtils.increaseBalance(token, depositTotalDTO.getAmount());

        deposit.setBalance(deposit.getBalance().subtract(depositTotalDTO.getAmount()));
        DepositEntity entity = depositRepository.save(deposit);
        return new DepositTotalDTO(depositTotalDTO.getId(), entity.getBalance());
    }

    @Logging
    private DepositEntity findDepositInList(Long id, List<DepositEntity> list) throws DepositNotFountException {
        DepositEntity deposit;
        Optional<DepositEntity> depositOptional = list.stream()
                .filter(it -> it.getId().equals(id))
                .findFirst();
        if (depositOptional.isPresent()) {
            deposit = depositOptional.get();
        } else throw new DepositNotFountException("deposit with id " + id + " has no rights");
        return deposit;
    }

    @Logging
    @Override
    public List<DepositEntity> getCustomerDeposits(Long customerId) {
        List<DepositEntity> list = depositRepository.findAllByCustomerId(customerId);
        return list;
    }

}
