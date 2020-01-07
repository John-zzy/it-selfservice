package com.cnec5.it.selfservice.server.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 登录后获取用户部分信息
 */
@Data
public class LoginInfo implements Serializable {

    /**
     * 登录用户名
     */
    private String name;

    /**
     * 登录头像
     */
    private String avatar;

    /**
     * 登录昵称
     */
    private String nickName;

}
