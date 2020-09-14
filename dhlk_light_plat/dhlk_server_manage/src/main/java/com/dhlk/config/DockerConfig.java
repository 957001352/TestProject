package com.dhlk.config;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DockerClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: server
 * @description: docker连接
 * @author: wqiang
 * @create: 2020-07-09 18:39
 **/
//@Configuration
public class DockerConfig {

    @Value("${attachment.dockerPath}")
    private String dockerPath;

    @Bean
    public DockerClient dockerClient(){
        return DockerClientBuilder.getInstance(dockerPath).build();
    }
}
