package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@ImportResource(value= {"classpath:com/egovframework/spring/context-common.xml",
        "classpath:com/egovframework/spring/context-hibernate.xml"
})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


}