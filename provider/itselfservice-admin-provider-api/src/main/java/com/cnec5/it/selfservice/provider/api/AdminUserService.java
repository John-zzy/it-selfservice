package com.cnec5.it.selfservice.provider.api;

import com.cnec5.it.selfservice.dto.AddUserParams;
import com.cnec5.it.selfservice.dto.UpdateParam;
import com.cnec5.it.selfservice.provider.domain.AdminUser;
import com.github.pagehelper.PageInfo;

/**
 * AdminUser 接口类
 */
public interface AdminUserService {

    /**
     * 获取用户
     *
     * @param id id
     * @return {@link AdminUser}
     */
    AdminUser get(Long id);

    /**
     * 获取用户
     *
     * @param username 用户名
     * @return {@link AdminUser}
     */
    AdminUser get(String username);

    /**
     * 获取用户
     *
     * @param adminUser {@link AdminUser}
     * @return {@link AdminUser}
     */
    AdminUser get(AdminUser adminUser);

    /**
     * 更新用户信息
     * <p>
     * 仅允许更新 邮箱、昵称、备注、状态
     *
     * @param adminUser {@link AdminUser}
     * @return 大于0则更新成功
     */
    int update(AdminUser adminUser);

    /**
     * 更新用户信息
     *
     * @param id    id
     * @param param {@link UpdateParam}
     * @return
     */
    int update(Long id, UpdateParam param);

    /**
     * 根据查询条件分页获取用户列表
     *
     * @param query        用户名或昵称查询参数
     * @param pageNum      当前页码
     * @param pageSize     每页显示条数
     * @return
     */
    PageInfo<AdminUser> page(String query, int pageNum, int pageSize);

    /**
     * 用户列表总记录数
     *
     * @return
     */
    int total();

    /**
     * 更新用户状态
     *
     * @param id     id
     * @param status 状态码
     * @return 大于0 则更新成功
     */
    int updateStatus(Long id, Integer status);

    /**
     * 新增用户
     *
     * @param addUserParams {@link AddUserParams}
     * @return 大于0 则更新成功
     */
    int insert(AddUserParams addUserParams);

    /**
     * 删除用户信息
     *
     * @param id id
     * @return 大于0 则更新成功
     */
    int delete(Long id);

}
