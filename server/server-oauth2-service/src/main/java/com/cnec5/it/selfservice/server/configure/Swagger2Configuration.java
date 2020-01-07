package com.cnec5.it.selfservice.server.configure;

import com.cnec5.it.selfservice.common.constant.Swagger2Constants;
import com.google.common.collect.Lists;
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
 * OAuth2 Controller Swagger2 配置
 */
@Configuration
@EnableSwagger2
public class Swagger2Configuration {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.cnec5.it.selfservice.server.controller"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemas())
                .securityContexts(securityContexts());
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("OAuth2 API 接口文档")
                .description("OAuth2 API 网关接口, 地址http://localhost:8888/api/")
                .termsOfServiceUrl("localhost")
                .version(Swagger2Constants.SWAGGER_VERSION)
                .build();
    }

    /**
     * 用于swagger上进行授权
     *
     * @return
     */
    // TODO: 在swagger2中进行API测试功能未完成
    private List<SecurityContext> securityContexts() {
        List<SecurityContext> securityContexts = Lists.newArrayList();
        securityContexts.add(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .forPaths(PathSelectors.regex("^(?!auth).*$"))
                        .build()
        );
        return securityContexts;
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> securityReferences = new ArrayList<>();
        securityReferences.add(new SecurityReference("Authorization", authorizationScopes));
        return securityReferences;
    }

    private List<ApiKey> securitySchemas() {
        List<ApiKey> apiKeyList = Lists.newArrayList();
        apiKeyList.add(new ApiKey("Authorization", "Authorization", "header"));
        return apiKeyList;
    }
}
