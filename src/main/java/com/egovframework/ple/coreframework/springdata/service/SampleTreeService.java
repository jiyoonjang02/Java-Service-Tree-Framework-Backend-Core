package com.egovframework.ple.coreframework.springdata.service;

import com.egovframework.ple.coreframework.springdata.model.SampleTreeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SampleTreeService extends TreeService {

    Long saveNewNode(SampleTreeEntity sampleTreeEntity);

    Page<SampleTreeEntity> findAllBy(SampleTreeEntity sampleTreeEntity, Pageable page);

    List<SampleTreeEntity> findAllBy(SampleTreeEntity sampleTreeEntity);

}
