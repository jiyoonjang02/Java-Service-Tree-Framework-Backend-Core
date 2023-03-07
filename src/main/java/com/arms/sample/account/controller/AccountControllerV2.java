package com.arms.sample.account.controller;
import com.arms.sample.account.model.AccountEntity;
import com.arms.sample.account.service.AccountServiceV2;
import com.arms.sample.common.CommonResponse;
import com.arms.sample.common.validate.InsertGroup;
import com.egovframework.ple.core.mapper.TreeMapperDao;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v2/online")
@RestController
@AllArgsConstructor
public class AccountControllerV2 {

    private final AccountServiceV2 accountServiceV2;

    @PostMapping("/account")
    public ResponseEntity<?> join(@RequestBody @Validated(value = InsertGroup.class) AccountEntity accountDTO){
        return ResponseEntity
                .ok(CommonResponse.success(accountServiceV2.join(accountDTO)));
    }

    @GetMapping("/account/")
    public ResponseEntity<?> findAccount(AccountEntity accountDTO){
        return ResponseEntity
                .ok(CommonResponse.success(accountServiceV2.findAllBy(accountDTO)));
    }

    @GetMapping("/account/paging")
    public ResponseEntity<?> findPagingAccount(AccountEntity accountDTO, Pageable pageable){
        return ResponseEntity
                .ok(CommonResponse.success(accountServiceV2.findAllBy(accountDTO,pageable)));
    }

    @Autowired
    TreeMapperDao mapper;

    @GetMapping("/mybatis")
    public ResponseEntity<?> mybatis(){
        return ResponseEntity
                .ok(CommonResponse.success(mapper.getListSample()));
    }


}
