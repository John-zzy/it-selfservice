package com.cnec5.it.selfservice.gateway.configure;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Primary
@AllArgsConstructor
public class SwaggerProvider implements SwaggerResourcesProvider {

    private static final String API_URI = "/v2/api-docs";
    private final RouteLocator routeLocator;
    // private final GatewayProperties gatewayProperties;

    @Value("${spring.application.name}")
    private String self;

    @Autowired
    public SwaggerProvider(RouteLocator routeLocator) {
        this.routeLocator = routeLocator;
    }


    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        List<String> routeHosts = new ArrayList<>();
        // 由于我的网关采用的是负载均衡的方式，因此我需要拿到所有应用的serviceId
        // 获取所有可用的host：serviceId
        routeLocator.getRoutes().filter(route -> route.getUri().getHost() != null)
                .filter(route -> !self.equals(route.getUri().getHost()))
                .subscribe(route -> routeHosts.add(route.getUri().getHost()));

        // 记录已经添加过的server，存在同一个应用注册了多个服务在nacos上
        Set<String> dealed = new HashSet<>();
        routeHosts.forEach(instance -> {
            // 拼接url，样式为/serviceId/v2/api-info，当网关调用这个接口时，会自动通过负载均衡寻找对应的主机
            String url = "/" + instance + API_URI;
            if (!dealed.contains(url)) {
                dealed.add(url);
                SwaggerResource swaggerResource = new SwaggerResource();
                swaggerResource.setUrl(url);
                swaggerResource.setName(instance);
                resources.add(swaggerResource);
            }
        });
        return resources;
    }


//    @Override
//    public List<SwaggerResource> get() {
//        List<SwaggerResource> resources = new ArrayList<>();
//        List<String> routes = new ArrayList<>();
//        routeLocator.getRoutes().subscribe(route -> routes.add(route.getId()));
//        gatewayProperties.getRoutes().stream().filter(routeDefinition -> routes.contains(routeDefinition.getId()))
//                .forEach(routeDefinition -> routeDefinition.getPredicates().stream()
//                        .filter(predicateDefinition -> ("Path").equalsIgnoreCase(predicateDefinition.getName()))
//                        .filter(predicateDefinition -> !"uranus-config".equalsIgnoreCase(routeDefinition.getId()))
//                        .forEach(predicateDefinition -> resources.add(swaggerResource(routeDefinition.getId(),
//                                predicateDefinition.getArgs().get(NameUtils.GENERATED_NAME_PREFIX + "0")
//                                        .replace("/**", API_URI)))));
//        return resources;
//    }
//
//    private SwaggerResource swaggerResource(String name, String location) {
//        SwaggerResource swaggerResource = new SwaggerResource();
//        swaggerResource.setName(name);
//        swaggerResource.setLocation(location);
//        swaggerResource.setSwaggerVersion("2.0");
//        return swaggerResource;
//    }
}
