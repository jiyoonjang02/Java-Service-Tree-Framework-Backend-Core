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
package com.arms.armsinstaller.controller;

import com.arms.armsinstaller.service.FileRepositoryInstaller;
import com.arms.armsinstaller.service.JiraConnectInfoInstaller;
import com.egovframework.ple.treeframework.controller.CommonResponse;
import com.egovframework.ple.treeframework.controller.TreeAbstractController;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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

    @Autowired
    @Qualifier("fileRepositoryInstaller")
    private FileRepositoryInstaller fileRepositoryInstaller;

    @Autowired
    @Qualifier("jiraConnectInfoInstaller")
    private JiraConnectInfoInstaller jiraConnectInfoInstaller;

    @PostConstruct
    public void initialize() {
        setTreeService(armsInstaller);
    }

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/makeDatabase")
    public ResponseEntity<?> makeDatabase() throws Exception {

        fileRepositoryInstaller.makeDataBase();
        jiraConnectInfoInstaller.makeDataBase();

        return ResponseEntity
                .ok(CommonResponse.success(armsInstaller.makeDataBase()));
    }

}
