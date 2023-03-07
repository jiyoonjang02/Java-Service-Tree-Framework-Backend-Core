package com.arms.samplespringdata.dao;


import java.util.Optional;

import com.arms.samplespringdata.model.SpringDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

interface SpringDataJpaRepository extends JpaRepository<SpringDataEntity,String>, JpaSpecificationExecutor<SpringDataEntity> {
    Optional<SpringDataEntity> findByUserName(String userName);
}
