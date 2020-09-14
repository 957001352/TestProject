package com.dhlk.hive.dhlk_hive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableDiscoveryClient
@ComponentScan("com.dhlk.hive")
@SpringBootApplication
public class DhlkHiveApplication {

	public static void main(String[] args) {
		SpringApplication.run(DhlkHiveApplication.class, args);
	}

}
