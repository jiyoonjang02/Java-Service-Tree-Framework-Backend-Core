package com.egovframework.ple.coreframework.springdata.service;


import com.egovframework.ple.coreframework.springdata.model.TreeSearchEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface TreeService {

    public <T extends TreeSearchEntity> T getNode(T coreSearchDTO) throws Exception;

    public <T extends TreeSearchEntity> int updateNode(T coreSearchDTO) throws Exception;

    public <T extends TreeSearchEntity> List<T> getChildNode(T coreSearchDTO) throws Exception;

    public <T extends TreeSearchEntity> List<T> getChildNodeWithoutPaging(T coreSearchDTO) throws Exception;

    public <T extends TreeSearchEntity> List<T> getPaginatedChildNode(T coreSearchDTO) throws Exception;

    public <T extends TreeSearchEntity> List<String> searchNode(T coreSearchDTO) throws Exception;

    public <T extends TreeSearchEntity> T addNode(T coreSearchDTO) throws Exception;

    public <T extends TreeSearchEntity> int removeNode(T coreSearchDTO) throws Exception;

    public <T extends TreeSearchEntity> int alterNode(T coreSearchDTO) throws Exception;

    public <T extends TreeSearchEntity> int alterNodeType(T coreSearchDTO) throws Exception;

    public <T extends TreeSearchEntity> T moveNode(T coreSearchDTO, HttpServletRequest request) throws Exception;

}
