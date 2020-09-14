package com.dhlk.hadoop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description Swagger接口调试
 * @Author lpsong
 * @Date 2020/3/12
 */
@Configuration
@EnableSwagger2
public class SwaggerCofing {

    /*@Value("${eureka.instance.instanceId}")*/
    @Value("${spring.cloud.consul.discovery.instance-id}")
    private String swaggerURL;
    /**
     * 设置开关，生产版本为false，关闭swagger
     */
    private boolean ebable=true;


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("大航联科")
                .description("http://" + swaggerURL + "/")
                .termsOfServiceUrl("http://" + swaggerURL + "/")
                .contact("local")
                .version("1.0")
                .build();
    }
    @Bean
    public Docket platformApi() {

        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).enable(ebable).forCodeGeneration(true)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.dhlk.hadoop.controller"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());


    }

    private List<ApiKey> securitySchemes() {
        List<ApiKey> apiKeyList= new ArrayList();
        apiKeyList.add(new ApiKey("Authorization", "Authorization", "header"));
        return apiKeyList;
    }

    private List<SecurityContext> securityContexts() {
        List<SecurityContext> securityContexts=new ArrayList<>();
        securityContexts.add(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .forPaths(PathSelectors.regex("^(?!auth).*$"))
                        .build());
        return securityContexts;
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> securityReferences=new ArrayList<>();
        securityReferences.add(new SecurityReference("Authorization", authorizationScopes));
        return securityReferences;
    }

}


