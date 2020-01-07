package com.cnec5.it.selfservice.server.service;

import com.cnec5.it.selfservice.provider.api.AdminUserService;
import com.cnec5.it.selfservice.provider.domain.AdminUser;
import com.google.common.collect.Lists;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 实现自定义用户名密码验证
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Reference(version = "1.0.0")
    private AdminUserService adminUserService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        // 查询用户
        AdminUser adminUser = adminUserService.get(s);

        List<GrantedAuthority> authorities = Lists.newArrayList();
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ADMINISTRATOR");
        authorities.add(grantedAuthority);

        if (adminUser != null) {
            return new User(adminUser.getUsername(), adminUser.getPassword(), authorities);
        }

        else {
            return null;
        }
    }
}
