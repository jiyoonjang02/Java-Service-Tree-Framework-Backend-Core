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
package com.arms.mappdservicenjira.controller;

import com.egovframework.ple.treeframework.controller.TreeAbstractController;
import com.egovframework.ple.treeframework.util.Util_TitleChecker;
import com.egovframework.ple.treeframework.validation.group.AddNode;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.PostConstruct;

import com.arms.mappdservicenjira.model.MapPdServiceNJiraEntity;
import com.arms.mappdservicenjira.service.MapPdServiceNJira;

import java.util.List;

@Slf4j
@Controller
@RequestMapping(value = {"/arms/mapPdServiceNJira"})
public class MapPdServiceNJiraController extends TreeAbstractController<MapPdServiceNJira, MapPdServiceNJiraEntity> {

    @Autowired
    @Qualifier("mapPdServiceNJira")
    private MapPdServiceNJira mapPdServiceNJira;

    @PostConstruct
    public void initialize() {
        setTreeService(mapPdServiceNJira);
    }

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ResponseBody
    @RequestMapping(value = "/getExistNode.do", method = RequestMethod.GET)
    public ModelAndView getExistNode(MapPdServiceNJiraEntity mapPdServiceNJiraEntity,
                                     BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors())
            throw new RuntimeException();

        //루트 노드를 기준으로 리스트를 검색
        MapPdServiceNJiraEntity tempSearchDTO = new MapPdServiceNJiraEntity();
        tempSearchDTO.setWhere("c_pdservice_id", mapPdServiceNJiraEntity.getC_pdservice_id());
        tempSearchDTO.setWhere("c_pdservice_version_id", mapPdServiceNJiraEntity.getC_pdservice_version_id());
        List<MapPdServiceNJiraEntity> list = mapPdServiceNJira.getChildNode(tempSearchDTO);

        for ( MapPdServiceNJiraEntity dto : list){
            String replaceTxt = dto.getC_pdservice_jira_ids().replaceAll("\\[","").replaceAll("\\]","");
            replaceTxt = replaceTxt.replaceAll("\"","");
            dto.setC_pdservice_jira_ids(replaceTxt);
        }

        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("result", list);

        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(
            value = {"/addNode.do"},
            method = {RequestMethod.POST}
    )
    public ModelAndView addNode(@Validated({AddNode.class}) MapPdServiceNJiraEntity mapPdServiceNJiraEntity,
                                BindingResult bindingResult, ModelMap model) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new RuntimeException();
        } else {
            mapPdServiceNJiraEntity.setC_title(Util_TitleChecker.StringReplace(mapPdServiceNJiraEntity.getC_title()));

            MapPdServiceNJiraEntity returnVO = mapPdServiceNJira.addNode(mapPdServiceNJiraEntity);

//            PropertiesReader propertiesReader = new PropertiesReader("egovframework/egovProps/globals.properties");
//            String armsUrl = "http://127.0.0.1:13131";
//            String targetUrl = "/callback/api/arms/armsScheduler/forceExec/set_PdServiceVersion_toJiraProjectVersion.do";
//
//            RestTemplate restTemplate = new RestTemplate();
//            ResponseEntity<String> response = restTemplate.getForEntity(armsUrl + targetUrl, String.class);
//            logger.info("response = " + response);

            ModelAndView modelAndView = new ModelAndView("jsonView");
            modelAndView.addObject("result", returnVO);
            return modelAndView;
        }
    }

}
