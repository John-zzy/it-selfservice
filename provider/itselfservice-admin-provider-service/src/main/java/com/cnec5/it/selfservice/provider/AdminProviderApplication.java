package com.cnec5.it.selfservice.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.cnec5.it.selfservice.provider.mapper")
public class AdminProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminProviderApplication.class, args);
    }
}
