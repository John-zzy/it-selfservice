package com.cnec5.it.selfservice.base;

import lombok.Data;

import java.io.Serializable;

/**
 * 交换机通用传输对象
 */
@Data
public class BaseSwichDto implements Serializable {

    /**
     * id
     */
    private Integer id;

    /**
     * 父id
     */
    private Integer parentId;

    /**
     * 位置
     */
    private String swiLocation;

    /**
     * 交换机IPV4地址
     */
    private String swiIpv4;

    /**
     * 交换机名称
     */
    private String swiName;

    /**
     * 备注
     */
    private String swiComment;

}
