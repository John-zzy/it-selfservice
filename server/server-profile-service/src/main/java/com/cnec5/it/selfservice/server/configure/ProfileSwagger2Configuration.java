package com.cnec5.it.selfservice.server.configure;

import com.cnec5.it.selfservice.common.constant.Swagger2Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Profile Controller Swagger2 配置
 */
@Configuration
@EnableSwagger2
public class ProfileSwagger2Configuration {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.cnec5.it.selfservice.server.controller"))
                .paths(PathSelectors.any())
                .build();
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Profile API 接口文档")
                .description("用户信息 API 网关接口, 地址http://localhost:8888/api/")
                .termsOfServiceUrl("localhost")
                .version(Swagger2Constants.SWAGGER_VERSION)
                .build();
    }

}
