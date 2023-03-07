package com.egovframework.ple.springdata.controller;

import com.egovframework.ple.springdata.model.SampleTreeEntity;
import com.egovframework.ple.springdata.service.SampleTreeService;
import com.egovframework.ple.springdata.util.Util_TitleChecker;
import com.egovframework.ple.springdata.validation.group.AddNode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;

@Slf4j
@Controller
@RestController
@AllArgsConstructor
@RequestMapping(value = {"/sample/tree"})
public class SampleTreeController extends TreeAbstractController<SampleTreeService, SampleTreeEntity> {

    @Autowired
    @Qualifier("sampleTreeService")
    private SampleTreeService sampleTreeService;


    @PostConstruct
    public void initialize() {
        setTreeService(sampleTreeService);
    }

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/paging")
    public ResponseEntity<?> findPagingAccount(SampleTreeEntity sampleTreeEntity, Pageable pageable){
        return ResponseEntity
                .ok(CommonResponse.success(sampleTreeService.findAllBy(sampleTreeEntity,pageable)));
    }


    @ResponseBody
    @RequestMapping(value = "/newNode.do", method = RequestMethod.POST)
    public ModelAndView addNode(@Validated(value = AddNode.class) SampleTreeEntity coreSearchDTO,
                                BindingResult bindingResult, ModelMap model) throws Exception {
        if (bindingResult.hasErrors())
            throw new RuntimeException();

        coreSearchDTO.setC_title(Util_TitleChecker.StringReplace(coreSearchDTO.getC_title()));

        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("result", sampleTreeService.saveNewNode(coreSearchDTO));
        return modelAndView;
    }

}