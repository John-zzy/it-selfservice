package com.cnec5.it.selfservice.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class IpListUpdateParam implements Serializable {

    /**
     * 端口号
     */
    private String portid;

    /**
     * 位置
     */
    private String location;

    /**
     * 楼层号
     */
    private String floorNum;

    /**
     * 房间号
     */
    private String roomNum;

    /**
     * 使用人
     */
    private String username;

    /**
     * IPV4地址
     */
    private String ipv4;

    /**
     * IPV6地址
     */
    private String ipv6;

    /**
     * MAC地址
     */
    private String mac;

    /**
     * 网络端口状态
     */
    private Integer status;

    /**
     * VLAN地址
     */
    private String swiVlan;

    /**
     * 交换机访问控制列表
     */
    private String swiAccesslist;

    /**
     * 交换机端口号
     */
    private Integer swiPortnum;

    /**
     * 交换机地址
     */
    private Integer swiAddress;

    /**
     * 备注
     */
    private String comment;

}
