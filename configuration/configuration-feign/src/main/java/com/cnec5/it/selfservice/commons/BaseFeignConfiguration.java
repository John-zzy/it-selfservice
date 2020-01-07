package com.cnec5.it.selfservice.commons;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * Feign资源服务全局配置
 */
public class BaseFeignConfiguration extends ResourceServerConfigurerAdapter {

    /**
     * 配置授权路径
     *
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .exceptionHandling()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 过滤Swagger2
                .antMatchers("/swagger-ui.html", "/swagger-resources/**", "/swagger/**", "/**/v2/api-docs",
                        "/**/*.js", "/**/*.css", "/**/*.png", "/**/*.ico", "/webjars/springfox-swagger-ui/**",
                        "/actuator/**").permitAll()
                .antMatchers("/**").hasAuthority("ADMINISTRATOR");
    }

    /**
     * 授权资源
     *
     * @param resources
     * @throws Exception
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        // 配置资源 ID
        resources.resourceId("backend-resources");
    }

}
