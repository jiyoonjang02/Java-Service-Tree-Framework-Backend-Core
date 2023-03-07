/*
 * @author Dongmin.lee
 * @since 2022-06-17
 * @version 22.06.17
 * @see <pre>
 *  Copyright (C) 2007 by 313 DEV GRP, Inc - All Rights Reserved
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *  Written by 313 developer group <313@313.co.kr>, December 2010
 * </pre>
 */
package com.arms.pdservice.service;

import com.arms.pdservice.model.PdServiceEntity;
import com.egovframework.ple.core.service.TreeService;

import java.util.List;

public interface PdService extends TreeService {

    public List<PdServiceEntity> getNodesWithoutRoot(PdServiceEntity pdServiceEntity) throws Exception;

    public PdServiceEntity addNodeToEndPosition(PdServiceEntity pdServiceEntity) throws Exception;

}