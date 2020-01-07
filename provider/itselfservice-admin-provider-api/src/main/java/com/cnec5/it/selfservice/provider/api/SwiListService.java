package com.cnec5.it.selfservice.provider.api;

import com.cnec5.it.selfservice.base.BaseSwichDto;
import com.cnec5.it.selfservice.provider.domain.SwiList;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 交换机列表管理接口类
 */
public interface SwiListService {

    /**
     * 查询所有交换机列表
     *
     * @return
     */
    List<SwiList> selectAll();

    /**
     * 查询所有交换机子集
     *
     * @return
     */
    List<SwiList> selectSwiLocation();

    /**
     * 创建交换机
     *
     * @param swiList {@link SwiList}
     * @return 大于0 则更新成功
     */
    @Transactional
    int insert(SwiList swiList);

    /**
     * 根据交换机的地址获取信息
     *
     * @return
     */
    SwiList get(String swiIpv4);

    /**
     * 根据ID查询获取交换机信息
     * @param id id
     * @return
     */
    SwiList get(Integer id);

    /**
     * 更新交换机信息
     *
     * @param id id
     * @param param {@link BaseSwichDto}
     * @return
     */
    @Transactional
    int update(Integer id, BaseSwichDto param);

    /**
     * 删除交换机信息
     *
     * @param swiList {@link SwiList}
     * @return
     */
    @Transactional
    int delete(SwiList swiList);
}
