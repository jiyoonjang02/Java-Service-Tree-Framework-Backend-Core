/*
 * @author Dongmin.lee
 * @since 2023-03-01
 * @version 23.03.01
 * @see <pre>
 *  Copyright (C) 2007 by 313 DEV GRP, Inc - All Rights Reserved
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *  Written by 313 developer group <313@313.co.kr>, December 2010
 * </pre>
 */
package com.arms.armsinstaller.controller;

import com.egovframework.ple.treeframework.springmybatis.dao.ISampleDAO;
import com.egovframework.ple.treeframework.springmybatis.vo.SampleEntity;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import com.egovframework.ple.treeframework.springhibernate.controller.SHVAbstractController;

import com.arms.armsinstaller.model.ArmsInstallerDTO;
import com.arms.armsinstaller.service.ArmsInstaller;

@Slf4j
@Controller
@RequestMapping(value = {"/arms/armsInstaller"})
public class ArmsInstallerController extends SHVAbstractController<ArmsInstaller, ArmsInstallerDTO> {

    @Autowired
    @Qualifier("armsInstaller")
    private ArmsInstaller armsInstaller;

    @Resource(name = "ISampleDAO")
    private ISampleDAO sampleDAO;

    @PostConstruct
    public void initialize() {
        setJsTreeHibernateService(armsInstaller);
    }

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ResponseBody
    @RequestMapping(
            value = {"/getMybatisSample.do"},
            method = {RequestMethod.GET}
    )
    public List<SampleEntity> getMybatisSample(ModelMap model, HttpServletRequest request) throws Exception {

        return sampleDAO.getListSample();

    }

}
