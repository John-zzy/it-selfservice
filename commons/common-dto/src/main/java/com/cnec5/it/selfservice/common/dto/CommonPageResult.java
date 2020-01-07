package com.cnec5.it.selfservice.common.dto;

import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页通用返回类
 * @param <T>
 */
@Data
public class CommonPageResult<T> implements Serializable {
    private Integer pageNum;
    private Integer pageSize;
    private Integer totalPage;
    private Long total;
    private List<T> list;

    /**
     * 将PageHelper分页后的list转为分页信息
     */
    public static <T> CommonPageResult<T> pageResult(PageInfo<T> pageInfo) {
        CommonPageResult<T> result = new CommonPageResult<T>();
        result.setPageSize(pageInfo.getPageSize());
        result.setPageNum(pageInfo.getPageNum());
        // 总记录数
        result.setTotal(pageInfo.getTotal());
        result.setTotalPage(pageInfo.getSize());
        result.setList(pageInfo.getList());
        return result;
    }

}
