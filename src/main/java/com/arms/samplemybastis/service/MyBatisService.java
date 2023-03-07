package com.arms.samplemybastis.service;

import com.arms.pdserviceversion.model.PdServiceVersionDTO;
import com.arms.samplemybastis.model.MyBatisEntity;

import java.util.List;

public interface MyBatisService {

    public List<MyBatisEntity> getList() throws Exception;

}
