package com.arms.samplespringdata.dao;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;

import com.arms.samplespringdata.model.SpringDataEntity;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class SpringDataRepository {
    private final SpringDataJpaRepository springDataJpaRepository;


    public SpringDataEntity save(SpringDataEntity springDataEntity) {
        SpringDataEntity save = springDataJpaRepository.save(springDataEntity);
        System.out.println(save);
        return springDataJpaRepository.save(springDataEntity);
    }

    public String delete(String userId) {
        springDataJpaRepository.deleteById(userId);
        return userId;
    }

    public SpringDataEntity findById(String id) {
        return springDataJpaRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public Optional<SpringDataEntity> findByUserName(String userName) {
        return springDataJpaRepository.findByUserName(userName);
    }

    public List<SpringDataEntity> findAllBy(Specification<SpringDataEntity> specification){
        return springDataJpaRepository.findAll(specification);
    }

    public Page<SpringDataEntity> findAllBy(Specification<SpringDataEntity> specification, Pageable pageable){
        return springDataJpaRepository.findAll(specification,pageable);
    }



}
