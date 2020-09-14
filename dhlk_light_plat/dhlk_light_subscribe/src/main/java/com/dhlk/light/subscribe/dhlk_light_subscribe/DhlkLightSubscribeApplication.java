package com.dhlk.light.subscribe.dhlk_light_subscribe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.dhlk.light.subscribe.service"})
@ComponentScan("com.dhlk.light")
@SpringBootApplication
public class DhlkLightSubscribeApplication {

    public static void main(String[] args) {
        SpringApplication.run(DhlkLightSubscribeApplication.class, args);
    }

}
