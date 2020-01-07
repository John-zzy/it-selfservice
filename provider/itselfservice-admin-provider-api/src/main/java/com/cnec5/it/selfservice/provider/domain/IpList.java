package com.cnec5.it.selfservice.provider.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "ip_list")
public class IpList implements Serializable {

    private static final long serialVersionUID = -3628651749716479235L;

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 端口号
     */
    @Column(name = "portid")
    private String portid;

    /**
     * 位置
     */
    @Column(name = "`location`")
    private String location;

    /**
     * 楼层号
     */
    @Column(name = "floor_num")
    private String floorNum;

    /**
     * 房间号
     */
    @Column(name = "room_num")
    private String roomNum;

    /**
     * 使用人
     */
    @Column(name = "username")
    private String username;

    /**
     * IPV4地址
     */
    @Column(name = "ipv4")
    private String ipv4;

    /**
     * IPV6地址
     */
    @Column(name = "ipv6")
    private String ipv6;

    /**
     * MAC地址
     */
    @Column(name = "mac")
    private String mac;

    /**
     * 网络端口状态
     */
    @Column(name = "`status`")
    private Integer status;

    /**
     * VLAN地址
     */
    @Column(name = "swi_vlan")
    private String swiVlan;

    /**
     * 交换机访问控制列表
     */
    @Column(name = "swi_accesslist")
    private String swiAccesslist;

    /**
     * 交换机端口号
     */
    @Column(name = "swi_portnum")
    private Integer swiPortnum;

    /**
     * 交换机地址
     */
    @Column(name = "swi_address")
    private Integer swiAddress;

    /**
     * 备注
     */
    @Column(name = "`comment`")
    private String comment;

    /**
     * 创建时间
     */
    @Column(name = "created")
    private Date created;

    /**
     * 更新时间
     */
    @Column(name = "updated")
    private Date updated;

}
