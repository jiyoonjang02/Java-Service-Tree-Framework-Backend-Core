package com.egovframework.ple.treeframework.springhibernate.controller;

/**
 * @author 이동민
 * @since 2021.12.18
 * @version 1.0
 * @see <pre>
 *  Copyright (C) 2007 by 313 DEV GRP, Inc - All Rights Reserved
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *  Written by 313 developer group <313@313.co.kr>, December 2010
 * </pre>
 * */

import com.google.common.collect.Maps;
import com.egovframework.ple.treeframework.springhibernate.service.JsTreeHibernateService;
import com.egovframework.ple.treeframework.springhibernate.util.Util_TitleChecker;
import com.egovframework.ple.treeframework.springhibernate.validation.group.*;
import com.egovframework.ple.treeframework.springhibernate.vo.JsTreeHibernateSearchDTO;
import com.egovframework.ple.treeframework.springhibernate.util.ParameterParser;
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

public abstract class SHVAbstractController<T extends JsTreeHibernateService, V extends JsTreeHibernateSearchDTO> extends GenericAbstractController {

    private T jsTreeHibernateService;
    private V returnVO;

    public void setJsTreeHibernateService( T jsTreeHibernateService) {
        this.jsTreeHibernateService = jsTreeHibernateService;
    }

    @ResponseBody
    @RequestMapping(value = "/getNode.do", method = RequestMethod.GET)
    public ModelAndView getNode(V jsTreeHibernateSearchDTO, HttpServletRequest request) throws Exception {

        ParameterParser parser = new ParameterParser(request);

        if (parser.getInt("c_id") <= 0) {
            throw new RuntimeException();
        }

        V returnVO = jsTreeHibernateService.getNode(jsTreeHibernateSearchDTO);

        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("result", returnVO);
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "/getChildNode.do", method = RequestMethod.GET)
    public ModelAndView getChildNode(V jsTreeHibernateSearchDTO, HttpServletRequest request)
            throws Exception {

        ParameterParser parser = new ParameterParser(request);

        if (parser.getInt("c_id") <= 0) {
            throw new RuntimeException();
        }

        jsTreeHibernateSearchDTO.setWhere("c_parentid", new Long(parser.get("c_id")));
        List<JsTreeHibernateSearchDTO> list = jsTreeHibernateService.getChildNode(jsTreeHibernateSearchDTO);

        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("result", list);
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "/getPaginatedChildNode.do", method = RequestMethod.GET)
    public ModelAndView getPaginatedChildNode(V paginatedJsTreeHibernateDTO, ModelMap model,
                                              HttpServletRequest request) throws Exception {

        if (paginatedJsTreeHibernateDTO.getC_id() <= 0 || paginatedJsTreeHibernateDTO.getPageIndex() <= 0
                || paginatedJsTreeHibernateDTO.getPageUnit() <= 0 || paginatedJsTreeHibernateDTO.getPageSize() <= 0) {
            throw new RuntimeException();
        }
        paginatedJsTreeHibernateDTO.setWhere("c_parentid", paginatedJsTreeHibernateDTO.getC_id());
        List<JsTreeHibernateSearchDTO> resultChildNodes = jsTreeHibernateService.getPaginatedChildNode(paginatedJsTreeHibernateDTO);
        paginatedJsTreeHibernateDTO.getPaginationInfo().setTotalRecordCount(resultChildNodes.size());

        ModelAndView modelAndView = new ModelAndView("jsonView");
        HashMap<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("paginationInfo", paginatedJsTreeHibernateDTO.getPaginationInfo());
        resultMap.put("result", resultChildNodes);
        modelAndView.addObject("result", resultMap);
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "/searchNode.do", method = RequestMethod.GET)
    public ModelAndView searchNode(V jsTreeHibernateSearchDTO, ModelMap model, HttpServletRequest request)
            throws Exception {

        ParameterParser parser = new ParameterParser(request);

        if (!StringUtils.hasText(request.getParameter("searchString"))) {
            throw new RuntimeException();
        }

        jsTreeHibernateSearchDTO.setWhereLike("c_title", parser.get("parser"));
        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("result", jsTreeHibernateService.searchNode(jsTreeHibernateSearchDTO));
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "/addNode.do", method = RequestMethod.POST)
    public ModelAndView addNode(@Validated(value = AddNode.class) V jsTreeHibernateSearchDTO,
                                BindingResult bindingResult, ModelMap model) throws Exception {
        if (bindingResult.hasErrors())
            throw new RuntimeException();

        jsTreeHibernateSearchDTO.setC_title(Util_TitleChecker.StringReplace(jsTreeHibernateSearchDTO.getC_title()));

        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("result", jsTreeHibernateService.addNode(jsTreeHibernateSearchDTO));
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "/removeNode.do", method = RequestMethod.DELETE)
    public ModelAndView removeNode(@Validated(value = RemoveNode.class) V jsTreeHibernateSearchDTO,
                                   BindingResult bindingResult, ModelMap model) throws Exception {
        if (bindingResult.hasErrors())
            throw new RuntimeException();

        jsTreeHibernateSearchDTO.setStatus(jsTreeHibernateService.removeNode(jsTreeHibernateSearchDTO));
        setJsonDefaultSetting(jsTreeHibernateSearchDTO);

        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("result", jsTreeHibernateSearchDTO);
        return modelAndView;
    }

    public void setJsonDefaultSetting( V jsTreeHibernateSearchDTO) {
        long defaultSettingValue = 0;
        jsTreeHibernateSearchDTO.setC_parentid(defaultSettingValue);
        jsTreeHibernateSearchDTO.setC_position(defaultSettingValue);
        jsTreeHibernateSearchDTO.setC_left(defaultSettingValue);
        jsTreeHibernateSearchDTO.setC_right(defaultSettingValue);
        jsTreeHibernateSearchDTO.setC_level(defaultSettingValue);
        jsTreeHibernateSearchDTO.setRef(defaultSettingValue);
    }

    @ResponseBody
    @RequestMapping(value = "/updateNode.do", method = RequestMethod.PUT)
    public ModelAndView updateNode(@Validated(value = UpdateNode.class) V jsTreeHibernateSearchDTO,
                                   BindingResult bindingResult, HttpServletRequest request, ModelMap model) throws Exception {

        if (bindingResult.hasErrors()) {
            throw new RuntimeException();
        }

        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("result", jsTreeHibernateService.updateNode(jsTreeHibernateSearchDTO));
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "/alterNode.do", method = RequestMethod.PUT)
    public ModelAndView alterNode(@Validated(value = AlterNode.class) V jsTreeHibernateSearchDTO,
                                  BindingResult bindingResult, ModelMap model) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new RuntimeException();
        }

        jsTreeHibernateSearchDTO.setC_title(Util_TitleChecker.StringReplace(jsTreeHibernateSearchDTO.getC_title()));

        jsTreeHibernateSearchDTO.setStatus(jsTreeHibernateService.alterNode(jsTreeHibernateSearchDTO));
        setJsonDefaultSetting(jsTreeHibernateSearchDTO);

        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("result", jsTreeHibernateSearchDTO);
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "/alterNodeType.do", method = RequestMethod.PUT)
    public ModelAndView alterNodeType(@Validated(value = AlterNodeType.class) V jsTreeHibernateSearchDTO,
                                      BindingResult bindingResult, ModelMap model) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new RuntimeException();
        }

        jsTreeHibernateService.alterNodeType(jsTreeHibernateSearchDTO);
        setJsonDefaultSetting(jsTreeHibernateSearchDTO);
        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("result", jsTreeHibernateSearchDTO);
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "/moveNode.do", method = RequestMethod.POST)
    public ModelAndView moveNode(@Validated(value = MoveNode.class) V jsTreeHibernateSearchDTO,
                                 BindingResult bindingResult, ModelMap model, HttpServletRequest request) throws Exception {
        if (bindingResult.hasErrors())
            throw new RuntimeException();

        jsTreeHibernateService.moveNode(jsTreeHibernateSearchDTO, request);
        setJsonDefaultSetting(jsTreeHibernateSearchDTO);

        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("result", jsTreeHibernateSearchDTO);
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
    public ModelAndView getMonitor(V jsTreeHibernateSearchDTO, ModelMap model, HttpServletRequest request)
            throws Exception {

        jsTreeHibernateSearchDTO.setOrder(Order.desc("c_id"));
        List<JsTreeHibernateSearchDTO> list = jsTreeHibernateService.getChildNode(jsTreeHibernateSearchDTO);

        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("result", list);
        return modelAndView;
    }

}