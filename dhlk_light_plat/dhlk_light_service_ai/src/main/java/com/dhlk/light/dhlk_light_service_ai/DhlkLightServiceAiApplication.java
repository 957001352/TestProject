package com.dhlk.light.dhlk_light_service_ai;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ComponentScan("com.dhlk")
@MapperScan(basePackages="com.dhlk.light.dao")
@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass=true)
@EnableDiscoveryClient
@EnableCaching
public class DhlkLightServiceAiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DhlkLightServiceAiApplication.class, args);
    }
}
