package com.egovframework.ple.core.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TreeMapperDao {
    public List<SampleMapperEntity> getListSample();
}

