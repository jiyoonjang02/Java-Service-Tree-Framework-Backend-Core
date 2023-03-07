package com.arms.samplespringdata.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.criteria.Predicate;

import com.arms.samplespringdata.dao.SpringDataRepository;
import com.arms.samplespringdata.model.SpringDataEntity;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class SpringDataServiceImpl implements SpringDataService {

    private final SpringDataRepository springDataRepository;

    @Override
    @Transactional(transactionManager = "transactionJpaManager")
    public String join(SpringDataEntity springDataEntity) {
        try {
            return springDataRepository.save(springDataEntity).getUserId();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    @Override
    @Transactional(transactionManager = "transactionJpaManager")
    public String cancel(String userId) {
        SpringDataEntity springDataEntity = springDataRepository.findById(userId);
        springDataEntity.cancel();
        return springDataRepository.save(springDataEntity).getUserId();
    }

    @Override
    @Transactional(transactionManager = "transactionJpaManager")
    public String update(SpringDataEntity springDataEntity) {
        return springDataRepository.save(springDataEntity).getUserId();
    }

    @Override
    @Transactional(transactionManager = "transactionJpaManager")
    public String delete(String userId) {
        return springDataRepository.delete(userId);
    }


    @Override
    public Page<SpringDataEntity> findAllBy(SpringDataEntity springDataEntity, Pageable pageable) {
        return springDataRepository.findAllBy(
                        searchWith(springDataEntity), pageable);
    }

    @Override
    public List<SpringDataEntity> findAllBy(SpringDataEntity accountDTO) {
        return springDataRepository.findAllBy(
                        searchWith(accountDTO))
                .stream().collect(Collectors.toList());
    }

    private Specification<SpringDataEntity> searchWith(SpringDataEntity accountDTO
    ) {
        return ((root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (ObjectUtils.isNotEmpty(accountDTO.getUserId())) {
                predicates.add(builder.equal(root.get("userId"), accountDTO.getUserId()));
            }
            if (ObjectUtils.isNotEmpty(accountDTO.getEmail())) {
                predicates.add(builder.equal(root.get("email"), accountDTO.getEmail()));
            }
            if (ObjectUtils.isNotEmpty(accountDTO.getUserName())) {
                predicates.add(builder.like(root.get("userName"), "%" + accountDTO.getUserName() + "%"));
            }
            return builder.and(predicates.toArray(new Predicate[0]));
        });
    }

}
