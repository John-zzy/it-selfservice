package com.cnec5.it.selfservice.server.controller;

import com.cnec5.it.selfservice.common.dto.ResponseResult;
import com.cnec5.it.selfservice.dto.UpdateParam;
import com.cnec5.it.selfservice.provider.api.AdminUserService;
import com.cnec5.it.selfservice.provider.domain.AdminUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户信息Controller
 */
@Api(tags = "ProfileController", description = "用户信息API")
@RestController
@RequestMapping(value = "profile")
public class ProfileController {

    @Reference(version = "1.0.0")
    private AdminUserService adminUserService;

    /**
     * 获取用户信息
     *
     * @param username 用户名
     * @return
     */
    @ApiOperation(value = "获取用户信息")
    @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "string", paramType = "path")
    @GetMapping(value = "info/{username}")
    public ResponseResult<AdminUser> info(@PathVariable String username) {
        AdminUser adminUser = adminUserService.get(username);
        return new ResponseResult<AdminUser>(ResponseResult.CodeStatus.SUCCESS, "查询个人信息成功", adminUser);
    }

    /**
     * 更新用户信息
     *
     * @param updateParam {@link UpdateParam}
     * @return 大于0更新成功
     */
    @ApiOperation(value = "更新用户信息")
    @PostMapping(value = "update")
    public ResponseResult<Void> update(@RequestBody UpdateParam updateParam) {
        AdminUser adminUser = new AdminUser();
        BeanUtils.copyProperties(updateParam, adminUser);
        int result = adminUserService.update(adminUser);
        // 更新成功
        if (result > 0) {
            return new ResponseResult<Void>(ResponseResult.CodeStatus.SUCCESS, "更新用户信息成功");
        }
        // 更新失败
        else {
            return new ResponseResult<Void>(ResponseResult.CodeStatus.FAIL, "更新用户信息失败");
        }
    }


}
