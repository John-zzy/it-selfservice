package com.cnec5.it.selfservice.configure;

import com.cnec5.it.selfservice.interceptor.FeignRequestInterceptor;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * feign客户端全局配置
 */
@Configuration
public class FeignRequestConfiguration {

    @Bean
    public RequestInterceptor FeignRequestIntercepor() {
        return new FeignRequestInterceptor();
    }

}
