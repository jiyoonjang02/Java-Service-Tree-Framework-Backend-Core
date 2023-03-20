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
package com.arms.jiraproject.controller;

import com.egovframework.ple.treeframework.controller.TreeAbstractController;
import com.egovframework.ple.treeframework.util.FileHandler;
import com.egovframework.ple.treeframework.util.Util_TitleChecker;
import com.egovframework.ple.treeframework.validation.group.AddNode;
import com.egovframework.ple.treeframework.util.ParameterParser;
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

import com.arms.jiraproject.model.JiraProjectEntity;
import com.arms.jiraproject.service.JiraProject;

@Slf4j
@Controller
@RequestMapping(value = {"/arms/jiraProject"})
public class JiraProjectController extends TreeAbstractController<JiraProject, JiraProjectEntity> {

    @Autowired
    @Qualifier("jiraProject")
    private JiraProject jiraProject;

    @PostConstruct
    public void initialize() {
        setTreeService(jiraProject);
    }

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

//    @ResponseBody
//    @RequestMapping(value = "/getProjectList.do", method = RequestMethod.GET)
//    public ModelAndView getProjectList() throws Exception {
//
//
////        JiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
////        URI uri = new URI("http://www.313.co.kr/jira");
////        JiraRestClient jiraRestClient = factory.createWithBasicHttpAuthentication(uri, "admin", "flexjava");
////
////        Promise<Iterable<IssueType>> promise = jiraRestClient.getMetadataClient().getIssueTypes();
////        Iterable<IssueType> issueTypes = promise.claim();
////        for (IssueType it : issueTypes) {
////            System.out.println("Type ID = " + it.getId() + ", Name = " + it.getName());
////        }
//        final AsynchronousJiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
//        final URI jiraServerUri = new URI("http://www.313.co.kr/jira");
//        final JiraRestClient restClient = factory.createWithBasicHttpAuthentication(jiraServerUri, "admin", "flexjava");
//        final Issue issue = restClient.getIssueClient().getIssue("SP-689").claim();
//        logger.debug(issue.toString());
//
//        Iterable<BasicProject> test = restClient.getProjectClient().getAllProjects().claim();
//        getTransitionByName(test, "name");
//
//        ModelAndView modelAndView = new ModelAndView("jsonView");
//        modelAndView.addObject("result", test);
//
//        return modelAndView;
//    }
//
//    private BasicProject getTransitionByName(Iterable<BasicProject> transitions, String transitionName) {
//        for (BasicProject transition : transitions) {
//            logger.debug(transition.toString());
//            if (transition.getName().equals(transitionName)) {
//                return transition;
//            }
//        }
//        return null;
//    }

}
