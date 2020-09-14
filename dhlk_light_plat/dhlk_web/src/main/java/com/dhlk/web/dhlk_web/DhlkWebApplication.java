package com.dhlk.web.dhlk_web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.dhlk.web.*.service"})
@ComponentScan("com.dhlk.web")
public class DhlkWebApplication {
	@Autowired
	private DiscoveryClient discoveryClient;
	/**
	 * 获取所有服务
	 */
	@RequestMapping("/services")
	public Object services() {
		return discoveryClient.getServices();
	}

	public static void main(String[] args) {
		SpringApplication.run(DhlkWebApplication.class, args);
	}
}
