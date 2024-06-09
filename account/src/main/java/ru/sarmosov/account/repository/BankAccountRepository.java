package ru.sarmosov.account.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.sarmosov.account.entity.BankAccountEntity;

@Repository
public interface BankAccountRepository extends CrudRepository<BankAccountEntity, Long> {

}
