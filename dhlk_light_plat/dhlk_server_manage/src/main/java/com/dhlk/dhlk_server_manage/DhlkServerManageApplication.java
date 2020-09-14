package com.dhlk.dhlk_server_manage;

import com.dhlk.websockt.NettyServer;
import io.swagger.annotations.SwaggerDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan("com.dhlk")
@SwaggerDefinition
public class DhlkServerManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(DhlkServerManageApplication.class, args);
        try {
            System.out.println("建立wesocket");
            new NettyServer(9527).start();
        } catch (Exception e) {
            System.out.println("NettyServerError:" + e.getMessage());
        }
    }

}
