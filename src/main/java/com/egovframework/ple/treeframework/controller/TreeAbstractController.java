/*
 * @author Dongmin.lee
 * @since 2023-03-13
 * @version 23.03.13
 * @see <pre>
 *  Copyright (C) 2007 by 313 DEV GRP, Inc - All Rights Reserved
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *  Written by 313 developer group <313@313.co.kr>, December 2010
 * </pre>
 */
package com.egovframework.ple.treeframework.controller;

import com.egovframework.ple.treeframework.model.TreeSearchEntity;
import com.egovframework.ple.treeframework.service.TreeService;
import com.egovframework.ple.treeframework.util.ParameterParser;
import com.egovframework.ple.treeframework.util.Util_TitleChecker;
import com.egovframework.ple.treeframework.validation.group.*;
import com.google.common.collect.Maps;
import org.hibernate.criterion.Order;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

public abstract class TreeAbstractController<T extends TreeService, V extends TreeSearchEntity> extends TreeSupportController {

    private T treeService;
    private V returnVO;

    public void setTreeService(T treeService) {
        this.treeService = treeService;
    }

    @ResponseBody
    @RequestMapping(value = "/getNode.do", method = RequestMethod.GET)
    public ModelAndView getNode(V coreSearchDTO, HttpServletRequest request) throws Exception {

        ParameterParser parser = new ParameterParser(request);

        if (parser.getInt("c_id") <= 0) {
            throw new RuntimeException();
        }

        V returnVO = treeService.getNode(coreSearchDTO);

        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("result", returnVO);
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "/getChildNode.do", method = RequestMethod.GET)
    public ModelAndView getChildNode(V coreSearchDTO, HttpServletRequest request)
            throws Exception {

        ParameterParser parser = new ParameterParser(request);

        if (parser.getInt("c_id") <= 0) {
            throw new RuntimeException();
        }

        coreSearchDTO.setWhere("c_parentid", new Long(parser.get("c_id")));
        List<TreeSearchEntity> list = treeService.getChildNode(coreSearchDTO);

        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("result", list);
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "/getPaginatedChildNode.do", method = RequestMethod.GET)
    public ModelAndView getPaginatedChildNode(V paginatedTreeSearchEntity, ModelMap model,
                                              HttpServletRequest request) throws Exception {

        if (paginatedTreeSearchEntity.getC_id() <= 0 || paginatedTreeSearchEntity.getPageIndex() <= 0
                || paginatedTreeSearchEntity.getPageUnit() <= 0 || paginatedTreeSearchEntity.getPageSize() <= 0) {
            throw new RuntimeException();
        }
        paginatedTreeSearchEntity.setWhere("c_parentid", paginatedTreeSearchEntity.getC_id());
        List<TreeSearchEntity> resultChildNodes = treeService.getPaginatedChildNode(paginatedTreeSearchEntity);
        paginatedTreeSearchEntity.getPaginationInfo().setTotalRecordCount(resultChildNodes.size());

        ModelAndView modelAndView = new ModelAndView("jsonView");
        HashMap<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("paginationInfo", paginatedTreeSearchEntity.getPaginationInfo());
        resultMap.put("result", resultChildNodes);
        modelAndView.addObject("result", resultMap);
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "/searchNode.do", method = RequestMethod.GET)
    public ModelAndView searchNode(V coreSearchDTO, ModelMap model, HttpServletRequest request)
            throws Exception {

        ParameterParser parser = new ParameterParser(request);

        if (!StringUtils.hasText(request.getParameter("searchString"))) {
            throw new RuntimeException();
        }

        coreSearchDTO.setWhereLike("c_title", parser.get("parser"));
        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("result", treeService.searchNode(coreSearchDTO));
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "/addNode.do", method = RequestMethod.POST)
    public ModelAndView addNode(@Validated(value = AddNode.class) V coreSearchDTO,
                                BindingResult bindingResult, ModelMap model) throws Exception {
        if (bindingResult.hasErrors())
            throw new RuntimeException();

        coreSearchDTO.setC_title(Util_TitleChecker.StringReplace(coreSearchDTO.getC_title()));

        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("result", treeService.addNode(coreSearchDTO));
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "/removeNode.do", method = RequestMethod.DELETE)
    public ModelAndView removeNode(@Validated(value = RemoveNode.class) V coreSearchDTO,
                                   BindingResult bindingResult, ModelMap model) throws Exception {
        if (bindingResult.hasErrors())
            throw new RuntimeException();

        coreSearchDTO.setStatus(treeService.removeNode(coreSearchDTO));
        setJsonDefaultSetting(coreSearchDTO);

        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("result", coreSearchDTO);
        return modelAndView;
    }

    public void setJsonDefaultSetting(V coreSearchDTO) {
        long defaultSettingValue = 0;
        coreSearchDTO.setC_parentid(defaultSettingValue);
        coreSearchDTO.setC_position(defaultSettingValue);
        coreSearchDTO.setC_left(defaultSettingValue);
        coreSearchDTO.setC_right(defaultSettingValue);
        coreSearchDTO.setC_level(defaultSettingValue);
        coreSearchDTO.setRef(defaultSettingValue);
    }

    @ResponseBody
    @RequestMapping(value = "/updateNode.do", method = RequestMethod.PUT)
    public ModelAndView updateNode(@Validated(value = UpdateNode.class) V coreSearchDTO,
                                   BindingResult bindingResult, HttpServletRequest request, ModelMap model) throws Exception {

        if (bindingResult.hasErrors()) {
            throw new RuntimeException();
        }

        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("result", treeService.updateNode(coreSearchDTO));
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "/alterNode.do", method = RequestMethod.PUT)
    public ModelAndView alterNode(@Validated(value = AlterNode.class) V coreSearchDTO,
                                  BindingResult bindingResult, ModelMap model) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new RuntimeException();
        }

        coreSearchDTO.setC_title(Util_TitleChecker.StringReplace(coreSearchDTO.getC_title()));

        coreSearchDTO.setStatus(treeService.alterNode(coreSearchDTO));
        setJsonDefaultSetting(coreSearchDTO);

        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("result", coreSearchDTO);
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "/alterNodeType.do", method = RequestMethod.PUT)
    public ModelAndView alterNodeType(@Validated(value = AlterNodeType.class) V coreSearchDTO,
                                      BindingResult bindingResult, ModelMap model) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new RuntimeException();
        }

        treeService.alterNodeType(coreSearchDTO);
        setJsonDefaultSetting(coreSearchDTO);
        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("result", coreSearchDTO);
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "/moveNode.do", method = RequestMethod.POST)
    public ModelAndView moveNode(@Validated(value = MoveNode.class) V coreSearchDTO,
                                 BindingResult bindingResult, ModelMap model, HttpServletRequest request) throws Exception {
        if (bindingResult.hasErrors())
            throw new RuntimeException();

        treeService.moveNode(coreSearchDTO, request);
        setJsonDefaultSetting(coreSearchDTO);

        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("result", coreSearchDTO);
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "/analyzeNode.do", method = RequestMethod.GET)
    public ModelAndView analyzeNode(ModelMap model) {
        model.addAttribute("analyzeResult", "");

        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("result", "true");
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "/getMonitor.do", method = RequestMethod.GET)
    public ModelAndView getMonitor(V coreSearchDTO, ModelMap model, HttpServletRequest request)
            throws Exception {

        coreSearchDTO.setOrder(Order.desc("c_id"));
        List<TreeSearchEntity> list = treeService.getChildNode(coreSearchDTO);

        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("result", list);
        return modelAndView;
    }

}