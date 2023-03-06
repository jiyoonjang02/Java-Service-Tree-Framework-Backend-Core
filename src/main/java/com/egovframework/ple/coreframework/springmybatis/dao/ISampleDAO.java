package com.egovframework.ple.coreframework.springmybatis.dao;

import com.egovframework.ple.coreframework.springmybatis.vo.SampleEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ISampleDAO {
    public List<SampleEntity> getListSample();
}
