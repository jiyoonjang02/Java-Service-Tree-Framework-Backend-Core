package com.egovframework.ple.springdata.dao;

import com.egovframework.ple.springdata.model.TreeSearchEntity;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@SuppressWarnings("unchecked")
@Repository("treeDao")
public class TreeDaoImpl<T extends TreeSearchEntity> extends
        TreeAbstractDao<T, Serializable> implements TreeDao<T, Serializable> {

    private Class<T> clazz;

    public Class<T> getClazz() {
        return clazz;
    }

    public void setClazz(Class<T> clazzToSet) {
        this.clazz = clazzToSet;
    }

    @Override
    protected Class<T> getEntityClass() {
        return getClazz();
    }
}
