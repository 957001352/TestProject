package com.dhlk.light.factory.dhlk_light_factory;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@ComponentScan("com.dhlk")
@MapperScan(basePackages="com.dhlk.light.factory.dao")
@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class DhlkLightFactoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(DhlkLightFactoryApplication.class, args);
    }
}
