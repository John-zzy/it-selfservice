package com.cnec5.it.selfservice.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 创建交换机通用传输对象
 */
@Data
public class SwichParam implements Serializable {

    /**
     * 交换机端口数量
     */
    private Integer radio;

    /**
     * 交换机位置
     */
    private String position;

    /**
     * 交换机名称
     */
    private String swiName;

    /**
     * 交换机IPV4地址
     */
    private String swiAddress;

    /**
     * 创建交换机备注
     */
    private String swiComment;

}
