package com.arms.samplemybastis.service;

import com.arms.samplemybastis.mapper.MyBatisDao;
import com.arms.samplemybastis.model.MyBatisEntity;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("myBatisService")
public class MyBatisServiceImpl implements MyBatisService{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MyBatisDao mapper;


    @Override
    public List<MyBatisEntity> getList() throws Exception {
        return mapper.getListSample();
    }
}
