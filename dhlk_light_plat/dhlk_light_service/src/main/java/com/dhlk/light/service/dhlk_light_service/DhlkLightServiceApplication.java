package com.dhlk.light.service.dhlk_light_service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableDiscoveryClient
@ComponentScan("com.dhlk")
@MapperScan(basePackages="com.dhlk.light.service.dao")
@SpringBootApplication
@EnableCaching
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class DhlkLightServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(DhlkLightServiceApplication.class, args);
    }

}
