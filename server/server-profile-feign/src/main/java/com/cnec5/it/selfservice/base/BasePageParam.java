package com.cnec5.it.selfservice.base;

import lombok.Data;

import java.io.Serializable;

/**
 * PageHelper 通用分页参数
 */
@Data
public class BasePageParam implements Serializable {

    /**
     * 通用模糊查询参数
     */
    private String query;

    /**
     * 当前页码
     */
    private int pageNum;

    /**
     * 每页显示条数
     */
    private int pageSize;

}
