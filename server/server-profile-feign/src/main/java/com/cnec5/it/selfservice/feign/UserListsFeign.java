package com.cnec5.it.selfservice.feign;

import com.cnec5.it.selfservice.configure.FeignRequestConfiguration;
import com.cnec5.it.selfservice.dto.UsersParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 用户管理服务Feign客户端
 */
@FeignClient(value = "server-profile", path = "users", configuration = FeignRequestConfiguration.class)
public interface UserListsFeign {

    /**
     * 获取用户列表
     *
     * @param usersParam {@link UsersParam}
     * @return JSON
     */
    @PostMapping(value = "list")
    String userLists(@RequestBody UsersParam usersParam);

    /**
     * 更新用户状态
     *
     * @param id     id
     * @param status 状态码
     * @return JSON
     */
    @PutMapping("{id}/state/{status}")
    String updateStatus(@PathVariable Long id, @PathVariable Integer status);

}
