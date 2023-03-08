package com.arms.samplespringdata.controller;
import com.arms.samplespringdata.model.SpringDataEntity;
import com.arms.samplespringdata.service.SpringDataService;
import com.egovframework.ple.serviceframework.controller.CommonResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/arms/sample/springdata")
@RestController
@AllArgsConstructor
public class SampleSpringDataController {

    private final SpringDataService springDataService;

    @PostMapping("/account")
    public ResponseEntity<?> join(@RequestBody SpringDataEntity accountDTO){
        return ResponseEntity
                .ok(CommonResponse.success(springDataService.join(accountDTO)));
    }

    @GetMapping("/account/")
    public ResponseEntity<?> findAccount(SpringDataEntity accountDTO){
        return ResponseEntity
                .ok(CommonResponse.success(springDataService.findAllBy(accountDTO)));
    }

    @GetMapping("/account/paging")
    public ResponseEntity<?> findPagingAccount(SpringDataEntity accountDTO, Pageable pageable){
        return ResponseEntity
                .ok(CommonResponse.success(springDataService.findAllBy(accountDTO,pageable)));
    }

}
