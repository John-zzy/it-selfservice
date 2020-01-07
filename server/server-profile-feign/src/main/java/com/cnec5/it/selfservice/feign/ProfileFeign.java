package com.cnec5.it.selfservice.feign;

import com.cnec5.it.selfservice.configure.FeignRequestConfiguration;
import com.cnec5.it.selfservice.dto.UpdateParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 用户信息服务Feign接口
 */
@FeignClient(value = "server-profile", path = "profile", configuration = FeignRequestConfiguration.class)
public interface ProfileFeign {

    /**
     * 调用spring cloud服务 profile-service
     *
     * @param username 用户名
     * @return json
     */
    @GetMapping(value = "info/{username}")
    String info(@PathVariable String username);

    /**
     * 更新用户信息
     *
     * @param updateParam {@link UpdateParam}
     * @return
     */
    @PostMapping(value = "update")
    int update(@RequestBody UpdateParam updateParam);

}
