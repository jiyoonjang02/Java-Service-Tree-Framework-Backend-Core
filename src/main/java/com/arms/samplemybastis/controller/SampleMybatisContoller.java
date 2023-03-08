package com.arms.samplemybastis.controller;

import com.arms.samplemybastis.service.MyBatisService;
import com.egovframework.ple.treeframework.controller.CommonResponse;
import com.egovframework.ple.treeframework.controller.TreeSupportController;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Controller
@RestController
@AllArgsConstructor
@RequestMapping(value = {"/arms/sample/mybatis"})
public class SampleMybatisContoller extends TreeSupportController {

    @Autowired
    @Qualifier("myBatisService")
    private MyBatisService myBatisService;

    @GetMapping("/getList")
    public ResponseEntity<?> getList() throws Exception {
        return ResponseEntity
                .ok(CommonResponse.success(myBatisService.getList()));
    }

}
