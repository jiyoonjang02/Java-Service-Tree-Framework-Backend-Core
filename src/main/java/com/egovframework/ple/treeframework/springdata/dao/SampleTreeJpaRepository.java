package com.egovframework.ple.treeframework.springdata.dao;

import com.egovframework.ple.treeframework.springdata.model.SampleTreeEntity;
import com.egovframework.ple.treeframework.springdata.onlineV2.account.model.AccountEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

interface SampleTreeJpaRepository extends JpaRepository<SampleTreeEntity,Long>, JpaSpecificationExecutor<SampleTreeEntity> {
}
