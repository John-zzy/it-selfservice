package com.cnec5.it.selfservice.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.cnec5.it.selfservice.feign"})
public class ServerOauth2Application {
    public static void main(String[] args) {
        SpringApplication.run(ServerOauth2Application.class, args);
    }
}
