/*
 * @author Dongmin.lee
 * @since 2023-03-19
 * @version 23.03.19
 * @see <pre>
 *  Copyright (C) 2007 by 313 DEV GRP, Inc - All Rights Reserved
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *  Written by 313 developer group <313@313.co.kr>, December 2010
 * </pre>
 */
package com.arms.jiraissuestatelog.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.arms.jiraissuestatelog.model.JiraIssueStateLogEntity;

public interface JiraIssueStateLogJpaRepository extends JpaRepository<JiraIssueStateLogEntity,Long>, JpaSpecificationExecutor<JiraIssueStateLogEntity> {

}