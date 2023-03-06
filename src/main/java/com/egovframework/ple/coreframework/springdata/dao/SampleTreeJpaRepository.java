package com.egovframework.ple.coreframework.springdata.dao;

import com.egovframework.ple.coreframework.springdata.model.SampleTreeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

interface SampleTreeJpaRepository extends JpaRepository<SampleTreeEntity,Long>, JpaSpecificationExecutor<SampleTreeEntity> {
}
