package com.dhlk.web.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * 版本        修改时间        作者        修改内容
 * V1.0        ------        songlp       原始版本
 * 文件说明: FeignConfiguration
 **/


@Configuration
public class FeignConfiguration {
    private Logger logger = LoggerFactory.getLogger(FeignConfiguration.class);
    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                        .getRequestAttributes();
                HttpServletRequest request = attributes.getRequest();
                Enumeration<String> headerNames = request.getHeaderNames();
                if (headerNames != null) {
                    boolean flag = true;
                    while (headerNames.hasMoreElements()) {
                        String name = headerNames.nextElement();
                        if("x-forwarded-for".equals(name)){
                            flag = false;
                        }
                        String values = request.getHeader(name);
                        template.header(name, values);
                    }
                    if(flag){
                        template.header("x-forwarded-for",request.getRemoteAddr());
                    }
                }

                // 如果在Cookie内通过如下方式取
                Cookie[] cookies = request.getCookies();
                if (cookies != null && cookies.length > 0) {
                    for (Cookie cookie : cookies) {
                        template.header(cookie.getName(), cookie.getValue());
                    }
                }
            }
        };
    }



}