package egovframework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@ImportResource(value= {"classpath:egovframework/spring/com/context-aspect.xml",
        "classpath:egovframework/spring/com/context-common.xml",
        "classpath:egovframework/spring/com/context-hibernate.xml"
})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


}