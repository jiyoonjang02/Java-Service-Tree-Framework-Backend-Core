package com.egovframework.ple.serviceframework.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.egovframework.ple.serviceframework.util.ParameterParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public abstract class TreeSupportController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public ParameterParser getParameterParser(HttpServletRequest request){
        return new ParameterParser(request);
    }


    @ExceptionHandler(Exception.class)
    public void defenceException(Exception ex, HttpServletResponse response, HttpServletRequest request)
            throws IOException {

        response.setHeader("Expires", "-1");
        response.setHeader("Cache-Control", "must-revalidate, no-store, no-cache");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = response.getWriter();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", "Exception Catch");
        Gson gson = new GsonBuilder().serializeNulls().create();
        out.println(gson.toJson(map));
        out.flush();
        out.close();
        return;
    }

    @ExceptionHandler(RuntimeException.class)
    public void defenceRuntimeException(RuntimeException ex, HttpServletResponse response, HttpServletRequest request)
            throws IOException {

        response.setHeader("Expires", "-1");
        response.setHeader("Cache-Control", "must-revalidate, no-store, no-cache");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = response.getWriter();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", "RuntimeException Catch");
        if(ex.getMessage().isEmpty()){
            map.put("error", ex.getClass().toString());
        }else{
            map.put("error", ex.getMessage());
        }
        Gson gson = new GsonBuilder().serializeNulls().create();
        out.println(gson.toJson(map));
        out.flush();
        out.close();
        return;
    }

}
