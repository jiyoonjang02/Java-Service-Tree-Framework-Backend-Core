package com.config;

import com.egovframework.ple.treeframework.model.TreeBaseEntity;
import com.egovframework.ple.treeframework.util.PaginationInfo;
import com.fasterxml.classmate.TypeResolver;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.ModelMap;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Configuration
public class Swagger2Config {

    @Autowired
    private TypeResolver typeResolver;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .ignoredParameterTypes(ModelMap.class, HttpServletRequest.class)
                .directModelSubstitute(TreeBaseEntity.class, Enum.class)
                .directModelSubstitute(PaginationInfo.class, Enum.class)
                .directModelSubstitute(Projection.class, Enum.class)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .enable(true)
                .alternateTypeRules(
                        AlternateTypeRules.newRule(
                                typeResolver.resolve(List.class, Order.class),
                                typeResolver.resolve(Enum.class)
                        )
                );
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("PLE TreeFramework")
                .description(
                    "<span style='color: #e5603b;font-size: 18px;'><strong>&#8880; Select &trade;&#8881;</strong></span>\n\n" +

                    "&nbsp;&nbsp;&nbsp;&nbsp;<span style='color: #E49400; font-size: 16px;'>[ getNode.do ]</span>\n\n" +
                    "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style='color: #2477FF; font-size: 14px;'>&copy; c_id</span> -> ( c_id 값의 Node Return )\n\n" +

                    "&nbsp;&nbsp;&nbsp;&nbsp;<span style='color: #E49400; font-size: 16px;'>[ getChildNode.do ]</span>\n\n" +
                    "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style='color: #2477FF; font-size: 14px;'>&copy; c_id</span> -> ( c_id 값의 Node 하위 ( 자식 ) Node Return )\n\n" +

                    "&nbsp;&nbsp;&nbsp;&nbsp;<span style='color: #E49400; font-size: 16px;'>[ getPaginatedChildNode.do ]</span>\n\n" +
                    "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style='color: #2477FF; font-size: 14px;'>&copy; c_id</span> -> ( c_id 값의 Node 하위 ( 자식 ) Node를 페이징 처리하여 Return )\n\n" +

                    "<br><br>" +
                    "<span style='color: #e5603b;font-size: 15px;'><strong>&#8880; Insert&trade; &#8881;</strong></span>\n\n" +
                    "<span style='color:blue'>[addNode.do]</span>\n\n" +
                    "&nbsp;&nbsp;&nbsp;&nbsp;<span style='color:blue'>&copy;ref</span> -> ( 어느 부모 아이디 밑으로 추가할 건지 )\n\n" +
                    "&nbsp;&nbsp;&nbsp;&nbsp;<span style='color:blue'>&copy;c_position</span> -> ( 추가될 때 어느 포지션 위치 값으로 들어 갈 건지 )\n\n" +
                    "&nbsp;&nbsp;&nbsp;&nbsp;<span style='color:blue'>&copy;c_title</span> -> ( 추가될 노드의 값 )\n\n" +
                    "&nbsp;&nbsp;&nbsp;&nbsp;<span style='color:blue'>&copy;c_type</span> -> ( Branch 인 경우는 folder, Leaf 인 경우는 default )"
                )
                .contact(new Contact("mile", "milenote.tistory.com", "skn@futurenuri.com"))
                .version("1.0")
                .build();
    }
}
