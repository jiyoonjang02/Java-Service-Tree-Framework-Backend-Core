package com.egovframework.ple.treeframework.springdata.service;

import com.arms.pdservice.model.PdServiceDTO;
import com.egovframework.ple.treeframework.springdata.model.SampleTreeEntity;
import com.egovframework.ple.treeframework.springdata.onlineV2.account.model.AccountEntity;
import com.egovframework.ple.treeframework.springhibernate.service.CoreService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SampleTreeService extends TreeService {

    Long saveNewNode(SampleTreeEntity sampleTreeEntity);

    Page<SampleTreeEntity> findAllBy(SampleTreeEntity sampleTreeEntity, Pageable page);

    List<SampleTreeEntity> findAllBy(SampleTreeEntity sampleTreeEntity);

}
