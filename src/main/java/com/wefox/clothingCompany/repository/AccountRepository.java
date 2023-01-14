package com.wefox.clothingCompany.repository;

import com.wefox.clothingCompany.model.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<AccountEntity, Integer> {
    /**
     * Find account by ID - but with PESSIMISTIC_READ - it'll be locked immediately
     *
     * @param accountId account id
     * @return the Account if exists
     */
    @Lock(LockModeType.PESSIMISTIC_READ)
    public Optional<AccountEntity> findById(Integer accountId);

}
