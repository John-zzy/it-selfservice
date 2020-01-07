package com.cnec5.it.selfservice.interceptor;

import feign.Request;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.util.Enumeration;

/**
 * Feign 拦截器
 * <p>
 * 将token放入请求中进行访问
 */
public class FeignRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        // 获取请求
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();

        // 获取请求头
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                String value = request.getHeader(name);
                requestTemplate.header(name, value);
            }
        }

        Enumeration<String> parameterNames = request.getParameterNames();
        StringBuilder sb = new StringBuilder();
        if (parameterNames != null) {
            while (parameterNames.hasMoreElements()) {
                String name = parameterNames.nextElement();
                String value = request.getParameter(name);

                // 将Token放入请求头
                if ("access_token".equals(name)) {
                    requestTemplate.header("authorization", "Bearer " + value);
                }

                // 放入请求体
                else {
                    sb.append(name).append("=").append(value).append("&");
                }
            }
        }

        if (sb.length() > 0) {
            // 去掉最后一位
            sb.deleteCharAt(sb.length() - 1);
            requestTemplate.body(Request.Body.bodyTemplate(sb.toString(), Charset.defaultCharset()));
        }
    }

}
