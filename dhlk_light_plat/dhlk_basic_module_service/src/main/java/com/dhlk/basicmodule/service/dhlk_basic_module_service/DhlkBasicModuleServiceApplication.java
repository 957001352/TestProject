package com.dhlk.basicmodule.service.dhlk_basic_module_service;

import com.github.pagehelper.PageHelper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Properties;


@RestController
@EnableDiscoveryClient
@ComponentScan("com.dhlk")
@MapperScan(basePackages="com.dhlk.basicmodule.service.dao")
@SpringBootApplication
@EnableCaching
@EnableSwagger2
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class DhlkBasicModuleServiceApplication {
	@Autowired
	private DiscoveryClient discoveryClient;
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	/**
	 * 获取所有服务
	 */
	@RequestMapping("/services")
	public Object services() {
		return discoveryClient.getServices();
	}

	public static void main(String[] args) {
		SpringApplication.run(DhlkBasicModuleServiceApplication.class, args);
	}

}
