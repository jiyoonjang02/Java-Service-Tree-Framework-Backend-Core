package com.egovframework.ple.core.dao;

import com.egovframework.ple.core.model.SampleTreeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

interface SampleTreeJpaRepository extends JpaRepository<SampleTreeEntity,Long>, JpaSpecificationExecutor<SampleTreeEntity> {
}
