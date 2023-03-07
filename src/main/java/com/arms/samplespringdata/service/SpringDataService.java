package com.arms.samplespringdata.service;


import java.util.List;

import com.arms.samplespringdata.model.SpringDataEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SpringDataService {

    String join(SpringDataEntity accountDTO);

    String cancel(String userId);

    String update(SpringDataEntity accountDTO);

    String delete(String userId);

    Page<SpringDataEntity> findAllBy(SpringDataEntity accountDTO, Pageable page);

    List<SpringDataEntity> findAllBy(SpringDataEntity accountDTO);

}
