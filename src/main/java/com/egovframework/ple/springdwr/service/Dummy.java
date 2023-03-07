package com.egovframework.ple.springdwr.service;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.spring.SpringCreator;
import org.springframework.stereotype.Service;

@RemoteProxy(creator = SpringCreator.class, name = "Dummy")
@Service
public class Dummy {

    @RemoteMethod
    public String getHelloWorld() { return "DWR입네다!!!!"; }

}
