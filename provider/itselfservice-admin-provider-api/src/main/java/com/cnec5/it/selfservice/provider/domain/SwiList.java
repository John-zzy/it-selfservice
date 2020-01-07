package com.cnec5.it.selfservice.provider.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Data
@Table(name = "swi_list")
public class SwiList implements Serializable {

    private static final long serialVersionUID = 4482573708845842168L;

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 父ID
     */
    @Column(name = "parent_id")
    private Integer parentId;

    /**
     * 位置
     */
    @Column(name = "swi_location")
    private String swiLocation;

    /**
     * 交换机IPV4地址
     */
    @Column(name = "swi_ipv4")
    private String swiIpv4;

    /**
     * 交换机名称
     */
    @Column(name = "swi_name")
    private String swiName;

    /**
     * 备注
     */
    @Column(name = "swi_comment")
    private String swiComment;

    /**
     * 子类
     */
    private List<SwiList> children;

}
