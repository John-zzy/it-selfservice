package com.cnec5.it.selfservice.server.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 登录用户信息传输对象
 */
@Data
public class LoginParam implements Serializable {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

}
