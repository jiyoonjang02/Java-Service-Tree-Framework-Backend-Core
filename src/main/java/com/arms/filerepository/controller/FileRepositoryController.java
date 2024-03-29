/*
 * @author Dongmin.lee
 * @since 2022-11-04
 * @version 22.11.04
 * @see <pre>
 *  Copyright (C) 2007 by 313 DEV GRP, Inc - All Rights Reserved
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *  Written by 313 developer group <313@313.co.kr>, December 2010
 * </pre>
 */
package com.arms.filerepository.controller;

import com.arms.filerepository.model.FileRepositoryEntity;
import com.arms.filerepository.service.FileRepository;
import com.egovframework.ple.treeframework.controller.TreeAbstractController;
import com.egovframework.ple.treeframework.util.PropertiesReader;
import com.egovframework.ple.treeframework.util.ParameterParser;
import com.egovframework.ple.treeframework.util.EgovFormBasedFileUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Controller
@RequestMapping(value = {"/arms/fileRepository"})
public class FileRepositoryController extends TreeAbstractController<FileRepository, FileRepositoryEntity> {

    @Autowired
    @Qualifier("fileRepository")
    private FileRepository fileRepository;

    @PostConstruct
    public void initialize() {
        setTreeService(fileRepository);
    }

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ResponseBody
    @RequestMapping(value = "/getFilesByNode.do", method = RequestMethod.GET)
    public ModelAndView getFilesByNode(FileRepositoryEntity fileRepositoryEntity, HttpServletRequest request) throws Exception {

        ParameterParser parser = new ParameterParser(request);

        fileRepositoryEntity.setWhere("fileIdLink", parser.getLong("fileIdlink"));
        fileRepositoryEntity.setWhere("c_title", fileRepositoryEntity.getC_title());
        List<FileRepositoryEntity> list = fileRepository.getChildNode(fileRepositoryEntity);

        HashMap<String, List<FileRepositoryEntity>> map = new HashMap();
        map.put("files", list);

        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("result", map);

        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value="/deleteFileByNode/{fileId}", method = RequestMethod.DELETE)
    public ModelAndView deleteFileByNode(@PathVariable(value ="fileId") Long fileId, ModelMap model,
                                         HttpServletRequest request) throws Exception {

        FileRepositoryEntity fileRepositoryEntity = new FileRepositoryEntity();
        fileRepositoryEntity.setC_id(fileId);
        int result = fileRepository.removeNode(fileRepositoryEntity);

        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("result", result);
        return modelAndView;
    }



    @RequestMapping(value="/downloadFileByNode/{fileId}", method = RequestMethod.GET)
    public void downloadFile(@PathVariable(value ="fileId") Long fileId,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {

        PropertiesReader propertiesReader = new PropertiesReader("egovframework/egovProps/globals.properties");
        String uploadPath = propertiesReader.getProperty("Globals.fileStorePath");

        FileRepositoryEntity fileRepositoryEntity = new FileRepositoryEntity();
        fileRepositoryEntity.setWhere("c_id", fileId);
        FileRepositoryEntity returnFileRepositoryEntity = fileRepository.getNode(fileRepositoryEntity);

        EgovFormBasedFileUtil.downloadFile(response, uploadPath
                , returnFileRepositoryEntity.getServerSubPath()
                , returnFileRepositoryEntity.getPhysicalName()
                , returnFileRepositoryEntity.getContentType()
                , returnFileRepositoryEntity.getFileName());

    }

}
