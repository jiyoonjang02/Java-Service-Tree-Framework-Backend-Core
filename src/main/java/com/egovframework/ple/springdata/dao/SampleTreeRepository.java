package com.egovframework.ple.springdata.dao;

import com.egovframework.ple.springdata.model.SampleTreeEntity;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Repository
@AllArgsConstructor
public class SampleTreeRepository{
        private final SampleTreeJpaRepository sampleTreeJpaRepository;


        public SampleTreeEntity save(SampleTreeEntity accountEntity) {
            SampleTreeEntity save = sampleTreeJpaRepository.save(accountEntity);
            System.out.println(save);
            return sampleTreeJpaRepository.save(accountEntity);
        }

        public Long delete(Long nodeId) {
            sampleTreeJpaRepository.deleteById(nodeId);
            return nodeId;
        }

        public SampleTreeEntity findById(Long nodeId) {
            return sampleTreeJpaRepository.findById(nodeId)
                    .orElseThrow(EntityNotFoundException::new);
        }

        public List<SampleTreeEntity> findAllBy(Specification<SampleTreeEntity> specification){
            return sampleTreeJpaRepository.findAll(specification);
        }

        public Page<SampleTreeEntity> findAllBy(Specification<SampleTreeEntity> specification, Pageable pageable){
            return sampleTreeJpaRepository.findAll(specification,pageable);
        }
}
