/*
 * @author Dongmin.lee
 * @since 2023-03-07
 * @version 23.03.07
 * @see <pre>
 *  Copyright (C) 2007 by 313 DEV GRP, Inc - All Rights Reserved
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *  Written by 313 developer group <313@313.co.kr>, December 2010
 * </pre>
 */
package com.arms.armsinstaller.controller;

import com.egovframework.ple.serviceframework.controller.TreeAbstractController;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;

import com.arms.armsinstaller.model.ArmsInstallerEntity;
import com.arms.armsinstaller.service.ArmsInstaller;

@Slf4j
@Controller
@RequestMapping(value = {"/arms/armsInstaller"})
public class ArmsInstallerController extends TreeAbstractController<ArmsInstaller, ArmsInstallerEntity> {

    @Autowired
    @Qualifier("armsInstaller")
    private ArmsInstaller armsInstaller;

    @PostConstruct
    public void initialize() {
        setTreeService(armsInstaller);
    }

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

}
