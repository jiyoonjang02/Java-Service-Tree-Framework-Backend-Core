package com.config;

import com.egovframework.ple.treeframework.model.TreeBaseEntity;
import com.egovframework.ple.treeframework.model.TreePaginatedEntity;
import com.egovframework.ple.treeframework.model.TreeSearchEntity;
import com.egovframework.ple.treeframework.util.PaginationInfo;
import org.hibernate.criterion.Order;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.ModelMap;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.http.HttpServletRequest;

@Configuration
public class Swagger2Config {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .ignoredParameterTypes(ModelMap.class, HttpServletRequest.class)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .enable(true);
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("테스트 API 타이틀")
                .description("테스트 API 상세소개 및 사용법 등")
                .contact(new Contact("mile", "milenote.tistory.com", "skn@futurenuri.com"))
                .version("1.0")
                .build();
    }
}
