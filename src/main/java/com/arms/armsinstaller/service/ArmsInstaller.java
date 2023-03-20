/*
 * @author Dongmin.lee
 * @since 2023-03-08
 * @version 23.03.08
 * @see <pre>
 *  Copyright (C) 2007 by 313 DEV GRP, Inc - All Rights Reserved
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *  Written by 313 developer group <313@313.co.kr>, December 2010
 * </pre>
 */
package com.arms.armsinstaller.service;

import com.arms.armsinstaller.model.ArmsInstallerEntity;
import com.arms.samplemybastis.model.MyBatisEntity;
import com.egovframework.ple.treeframework.service.TreeService;

import java.util.List;

public interface ArmsInstaller extends TreeService {

    public Integer makeDataBase() throws Exception;

}