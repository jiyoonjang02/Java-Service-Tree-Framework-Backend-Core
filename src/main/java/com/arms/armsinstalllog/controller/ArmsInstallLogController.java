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
package com.arms.armsinstalllog.controller;

import com.egovframework.ple.core.controller.TreeAbstractController;
import com.egovframework.ple.core.util.FileHandler;
import com.egovframework.ple.core.util.Util_TitleChecker;
import com.egovframework.ple.core.validation.group.AddNode;
import com.egovframework.ple.core.util.ParameterParser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.PostConstruct;

import com.arms.armsinstalllog.model.ArmsInstallLogEntity;
import com.arms.armsinstalllog.service.ArmsInstallLog;

@Slf4j
@Controller
@RequestMapping(value = {"/arms/armsInstallLog"})
public class ArmsInstallLogController extends TreeAbstractController<ArmsInstallLog, ArmsInstallLogEntity> {

    @Autowired
    @Qualifier("armsInstallLog")
    private ArmsInstallLog armsInstallLog;

    @PostConstruct
    public void initialize() {
        setTreeService(armsInstallLog);
    }

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

}