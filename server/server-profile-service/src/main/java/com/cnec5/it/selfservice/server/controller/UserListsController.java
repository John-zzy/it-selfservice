package com.cnec5.it.selfservice.server.controller;

import com.cnec5.it.selfservice.common.dto.CommonPageResult;
import com.cnec5.it.selfservice.common.dto.ResponseResult;
import com.cnec5.it.selfservice.dto.AddUserParams;
import com.cnec5.it.selfservice.dto.UpdateParam;
import com.cnec5.it.selfservice.dto.UsersParam;
import com.cnec5.it.selfservice.provider.api.AdminUserService;
import com.cnec5.it.selfservice.provider.domain.AdminUser;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户列表Controller
 */
@Api(tags = "UserListsController", description = "用户管理API")
@RestController
@RequestMapping(value = "users")
public class UserListsController {

    @Reference(version = "1.0.0")
    private AdminUserService adminUserService;

    /**
     * 根据查询条件分页获取用户列表
     *
     * @param usersParam {@link UsersParam}
     * @return
     */
    @ApiOperation(value = "分页获取用户列表")
    @PostMapping(value = "list")
    public ResponseResult<CommonPageResult<AdminUser>> list(@RequestBody UsersParam usersParam) {
        String query = usersParam.getQuery();
        int pageNum = usersParam.getPageNum();
        int pageSize = usersParam.getPageSize();

        // 分页
        PageInfo<AdminUser> pageInfo = adminUserService.page(query, pageNum, pageSize);
        CommonPageResult<AdminUser> result = CommonPageResult.pageResult(pageInfo);

//        CommonPageResult<AdminUser> result = new CommonPageResult<>();
//        result.setPageSize(pageInfo.getPageSize());
//        result.setPageNum(pageInfo.getPageNum());
//        // 总记录数
//        result.setTotal(pageInfo.getTotal());
//        result.setTotalPage(pageInfo.getSize());
//        result.setList(pageInfo.getList());

        return new ResponseResult<>(ResponseResult.CodeStatus.SUCCESS, "获取用户列表", result);
    }

    /**
     * 更新用户状态
     *
     * @param id     用户id
     * @param status 状态码
     * @return
     */
    @ApiOperation(value = "更新用户状态")
    @PutMapping(value = "{id}/state/{status}")
    public ResponseResult<Void> updateStatus(@PathVariable Long id, @PathVariable Integer status) {
        int result = adminUserService.updateStatus(id, status);
        // 请求成功
        if (result > 0) {
            return new ResponseResult<>(ResponseResult.CodeStatus.SUCCESS, "更新用户状态成功");
        }
        // 请求失败
        else {
            return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "更新用户状态失败");
        }
    }

    /**
     * 创建用户
     *
     * @param addUserParams {@link AddUserParams}
     * @return
     */
    @ApiOperation(value = "创建用户")
    @PostMapping(value = "create")
    public ResponseResult<Void> insert(@RequestBody AddUserParams addUserParams) {
        int result = adminUserService.insert(addUserParams);
        // 请求成功
        if (result > 0) {
            return new ResponseResult<>(ResponseResult.CodeStatus.SUCCESS, "创建用户成功");
        }
        // 请求失败
        else {
            return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "创建用户失败");
        }
    }

    /**
     * 根据ID查询用户
     *
     * @param id 用户id
     * @return
     */
    @ApiOperation(value = "根据ID查询用户")
    @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long", example = "1")
    @GetMapping(value = "{id}")
    public ResponseResult<AdminUser> getById(@PathVariable Long id) {
        AdminUser adminUser = adminUserService.get(id);
        // 请求成功
        if (adminUser != null) {
            return new ResponseResult<>(ResponseResult.CodeStatus.SUCCESS, "查询用户", adminUser);
        }
        // 请求失败
        else {
            return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "用户不存在");
        }
    }

    /**
     * 根据ID更新用户信息
     *
     * @param id          用户id
     * @param updateParam {@link UpdateParam}
     * @return
     */
    @ApiOperation(value = "根据ID更新用户信息")
    @PutMapping(value = "{id}/update")
    public ResponseResult<Void> updateUser(@PathVariable Long id, @RequestBody UpdateParam updateParam) {
        int result = adminUserService.update(id, updateParam);
        // 请求成功
        if (result > 0) {
            return new ResponseResult<>(ResponseResult.CodeStatus.SUCCESS, "更新用户成功");
        }
        // 请求失败
        else {
            return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "更新用户失败");
        }
    }

    /**
     * 根据ID删除用户信息
     *
     * @param id 用户id
     * @return
     */
    @ApiOperation(value = "根据ID删除用户信息")
    @DeleteMapping(value = "{id}/delete")
    public ResponseResult<Void> deleteById(@PathVariable Long id) {
        int result = adminUserService.delete(id);
        // 请求成功
        if (result > 0) {
            return new ResponseResult<>(ResponseResult.CodeStatus.SUCCESS, "删除用户成功");
        }
        // 请求失败
        else {
            return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "删除用户失败");
        }
    }


}
