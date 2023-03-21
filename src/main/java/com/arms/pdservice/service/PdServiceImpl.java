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
import com.egovframework.ple.treeframework.service.TreeServiceImpl;
import lombok.AllArgsConstructor;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

@AllArgsConstructor
@Service("pdService")
public class PdServiceImpl extends TreeServiceImpl implements PdService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final Long ROOT_NODE_ID = new Long(2);
    private static final String NODE_TYPE = new String("default");
    private static final String REQ_PREFIX_TABLENAME_BY_PDSERVICE = new String("T_ARMS_REQADD_");
    private static final String REQ_PREFIX_TABLENAME_BY_PDSERVICE_STATUS = new String("T_ARMS_REQSTATUS_");

    @Override
    public List<PdServiceEntity> getNodesWithoutRoot(PdServiceEntity pdServiceEntity) throws Exception {
        pdServiceEntity.setOrder(Order.desc("c_id"));
        Criterion criterion = Restrictions.not(
                // replace "id" below with property name, depending on what you're filtering against
                Restrictions.in("c_id", new Object[] {new Long(1), new Long(2)})
        );
        pdServiceEntity.getCriterions().add(criterion);
        List<PdServiceEntity> list = this.getChildNode(pdServiceEntity);
        for (PdServiceEntity dto: list) {
            dto.setC_contents("force empty");
        }
        return list;
    }


    @Override
    public PdServiceEntity addNodeToEndPosition(PdServiceEntity pdServiceEntity) throws Exception {
        //루트 노드를 기준으로 리스트를 검색
        PdServiceEntity paramPdServiceEntity = new PdServiceEntity();
        paramPdServiceEntity.setWhere("c_parentid", ROOT_NODE_ID);
        List<PdServiceEntity> list = this.getChildNode(paramPdServiceEntity);

        //검색된 노드중 maxPosition을 찾는다.
        PdServiceEntity maxPositionPdServiceEntity = list
                .stream()
                .max(Comparator.comparing(PdServiceEntity::getC_position))
                .orElseThrow(NoSuchElementException::new);

        //노드 값 셋팅
        pdServiceEntity.setRef(ROOT_NODE_ID);
        pdServiceEntity.setC_position(maxPositionPdServiceEntity.getC_position() + 1);
        pdServiceEntity.setC_type(NODE_TYPE);

        return this.addNode(pdServiceEntity);
    }
}