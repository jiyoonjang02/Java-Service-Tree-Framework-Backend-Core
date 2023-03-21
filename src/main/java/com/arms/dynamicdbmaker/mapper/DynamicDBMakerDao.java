package com.arms.dynamicdbmaker.mapper;

import com.arms.dynamicdbmaker.model.DynamicDBMakerEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DynamicDBMakerDao {

    public void ddlLogExecute(DynamicDBMakerEntity dynamicDBMakerEntity);

    public void ddlOrgExecute(DynamicDBMakerEntity dynamicDBMakerEntity);

    public void dmlOrgExecute1(DynamicDBMakerEntity dynamicDBMakerEntity);
    public void dmlOrgExecute2(DynamicDBMakerEntity dynamicDBMakerEntity);

    public void triggerExecute(DynamicDBMakerEntity dynamicDBMakerEntity);

}
