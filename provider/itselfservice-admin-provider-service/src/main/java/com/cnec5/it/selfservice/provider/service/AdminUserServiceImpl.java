package com.cnec5.it.selfservice.provider.service;

import com.cnec5.it.selfservice.dto.AddUserParams;
import com.cnec5.it.selfservice.dto.UpdateParam;
import com.cnec5.it.selfservice.provider.api.AdminUserService;
import com.cnec5.it.selfservice.provider.domain.AdminUser;
import com.cnec5.it.selfservice.provider.mapper.AdminUserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * AdminUser 实现类 实现AdminUserService接口
 */
@Service(version = "1.0.0")
public class AdminUserServiceImpl implements AdminUserService {

    @Resource
    public AdminUserMapper adminUserMapper;

    @Resource
    public BCryptPasswordEncoder passwordEncoder;

    /**
     * 获取用户
     *
     * @param id id
     * @return {@link AdminUser}
     */
    @Override
    public AdminUser get(Long id) {
        Example example = new Example(AdminUser.class);
        example.createCriteria().andEqualTo("id", id);
        return adminUserMapper.selectOneByExample(example);
    }

    /**
     * 获取用户
     *
     * @param username 用户名
     * @return {@link AdminUser}
     */
    @Override
    public AdminUser get(String username) {
        Example example = new Example(AdminUser.class);
        example.createCriteria().andEqualTo("username", username);
        return adminUserMapper.selectOneByExample(example);
    }

    /**
     * 获取用户
     *
     * @param adminUser {@link AdminUser}
     * @return {@link AdminUser}
     */
    @Override
    public AdminUser get(AdminUser adminUser) {
        return adminUserMapper.selectOne(adminUser);
    }

    /**
     * 更新用户信息
     *
     * @param adminUser {@link AdminUser}
     * @return
     */
    @Override
    public int update(AdminUser adminUser) {
        // 获取更新前用户信息
        AdminUser oldAdminUser = get(adminUser.getUsername());
        // 更新
        oldAdminUser.setEmail(adminUser.getEmail());
        oldAdminUser.setNickName(adminUser.getNickName());
        oldAdminUser.setNote(adminUser.getNote());
        oldAdminUser.setStatus(adminUser.getStatus());
        return adminUserMapper.updateByPrimaryKey(oldAdminUser);
    }

    /**
     * 更新用户信息
     *
     * @param id    id
     * @param param {@link UpdateParam}
     * @return
     */
    @Override
    public int update(Long id, UpdateParam param) {
        // 获取旧用户
        AdminUser adminUser = get(id);
        // 更新数据
        adminUser.setUsername(param.getUsername());
        adminUser.setNickName(param.getNickName());
        adminUser.setEmail(param.getEmail());
        adminUser.setNote(param.getNote());
        return adminUserMapper.updateByPrimaryKey(adminUser);
    }

    /**
     * 根据查询条件分页获取用户列表
     *
     * @param query     用户名或昵称查询参数
     * @param pageNum   当前页码
     * @param pageSize  每页显示条数
     * @return
     */
    @Override
    public PageInfo<AdminUser> page(String query, int pageNum, int pageSize) {

        Example example = new Example(AdminUser.class);
        Example.Criteria criteria = example.createCriteria();

        if (StringUtils.isNotBlank(query)) {
            criteria.orLike("username", "%" + query + "%")
                    .orLike("nickName", "%" + query + "%");
        }
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(adminUserMapper.selectByExample(example));

    }

    /**
     * 用户列表总记录数
     *
     * @return
     */
    @Override
    public int total() {
        Example example = new Example(AdminUser.class);
        return adminUserMapper.selectCountByExample(example);
    }

    /**
     * 更新用户状态
     *
     * @param id     id
     * @param status 状态码
     * @return
     */
    @Override
    public int updateStatus(Long id, Integer status) {
        AdminUser adminUser = new AdminUser();
        adminUser.setId(id);
        AdminUser newUser = adminUserMapper.selectOne(adminUser);
        newUser.setStatus(status);
        return adminUserMapper.updateByPrimaryKey(newUser);
    }

    /**
     * 新增用户
     *
     * @param addUserParams {@link AddUserParams}
     * @return
     */
    @Override
    public int insert(AddUserParams addUserParams) {
        AdminUser adminUser = new AdminUser();
        BeanUtils.copyProperties(addUserParams, adminUser);
        adminUser.setCreateTime(new Date());
        adminUser.setStatus(1);
        // 查询是否有相同用户名的用户
        Example example = new Example(AdminUser.class);
        example.createCriteria().andEqualTo("username", adminUser.getUsername());
        List<AdminUser> adminUserList = adminUserMapper.selectByExample(example);
        if (adminUserList.size() > 0) return 0;
        // 无重复账号密码加密进行插入
        String encodePwd = passwordEncoder.encode(adminUser.getPassword());
        adminUser.setPassword(encodePwd);
        return adminUserMapper.insert(adminUser);
    }

    /**
     * 删除用户信息
     *
     * @param id id
     * @return 大于0 则更新成功
     */
    @Override
    public int delete(Long id) {
        Example example = new Example(AdminUser.class);
        example.createCriteria().andEqualTo("id", id);
        return adminUserMapper.deleteByExample(example);
    }


}
