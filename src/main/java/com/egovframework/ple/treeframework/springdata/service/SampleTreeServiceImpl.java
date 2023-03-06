package com.egovframework.ple.treeframework.springdata.service;

import com.egovframework.ple.treeframework.springdata.dao.SampleTreeRepository;
import com.egovframework.ple.treeframework.springdata.model.SampleTreeEntity;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service("sampleTreeService")
public class SampleTreeServiceImpl extends TreeServiceImpl implements SampleTreeService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final SampleTreeRepository sampleTreeRepository;

    @Override
    @Transactional(transactionManager = "transactionManager")
    public Long saveNewNode(SampleTreeEntity sampleTreeEntity) {
        try {
            SampleTreeEntity test = this.addNode(sampleTreeEntity);
            return test.getC_id();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    @Override
    public Page<SampleTreeEntity> findAllBy(SampleTreeEntity sampleTreeEntity, Pageable pageable) {
        return sampleTreeRepository.findAllBy(
                searchWith(sampleTreeEntity), pageable);
    }

    @Override
    public List<SampleTreeEntity> findAllBy(SampleTreeEntity sampleTreeEntity) {
        return sampleTreeRepository.findAllBy(
                        searchWith(sampleTreeEntity))
                .stream().collect(Collectors.toList());
    }

    private Specification<SampleTreeEntity> searchWith(SampleTreeEntity sampleTreeEntity
    ) {
        return ((root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
//            if (ObjectUtils.isNotEmpty(sampleTreeEntity.get())) {
//                predicates.add(builder.equal(root.get("userId"), accountDTO.getUserId()));
//            }
//            if (ObjectUtils.isNotEmpty(accountDTO.getEmail())) {
//                predicates.add(builder.equal(root.get("email"), accountDTO.getEmail()));
//            }
//            if (ObjectUtils.isNotEmpty(accountDTO.getUserName())) {
//                predicates.add(builder.like(root.get("userName"), "%" + accountDTO.getUserName() + "%"));
//            }
            return builder.and(predicates.toArray(new Predicate[0]));
        });
    }
}