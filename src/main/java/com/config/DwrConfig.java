package com.config;

import org.directwebremoting.spring.DwrSpringServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath:com/egovframework/spring/context-dwr.xml")
public class DwrConfig {

    @Bean
    public ServletRegistrationBean servletRegistrationBean() {



        DwrSpringServlet servlet = new DwrSpringServlet();
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(servlet, "/dwr/*");
        // Set to true to enable DWR to debug and enter the test page.
        registrationBean.addInitParameter("debug", "true");

        //pollAndCometEnabled set to true to increase the loadability of the server, although DWR has a mechanism to protect the server from overload.
        registrationBean.addInitParameter("pollAndCometEnabled", "true");
        registrationBean.addInitParameter("activeReverseAjaxEnabled", "true");

        registrationBean.addInitParameter("initApplicationScopeCreatorsAtStartup", "false");
        registrationBean.addInitParameter("jsonpEnabled", "false");
        registrationBean.addInitParameter("org.directwebremoting.extend.ScriptSessionManager", "com.egovframework.ple.treeframework.springDWR.util.ScriptSessionManager");
        registrationBean.addInitParameter("allowScriptTagRemoting", "true");

        registrationBean.addInitParameter("maxWaitAfterWrite", "60");
        return registrationBean;
    }

}
