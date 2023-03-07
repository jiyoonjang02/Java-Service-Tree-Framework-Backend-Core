package com.arms.sample.account.dao;


import java.util.Optional;

import com.arms.sample.account.model.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

interface AccountJpaRepository extends JpaRepository<AccountEntity,String>, JpaSpecificationExecutor<AccountEntity> {
    Optional<AccountEntity> findByUserName(String userName);
}
