package com.cnec5.it.selfservice.provider.api;

import com.cnec5.it.selfservice.dto.IpListUpdateParam;
import com.cnec5.it.selfservice.provider.domain.IpList;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * IP地址管理接口类
 */
public interface IpListService {

    /**
     * 获取所有交换机IP列表
     *
     * @return
     */
    List<IpList> selectAll();

    /**
     * 根据查询条件分页获取IP地址列表
     *
     * @param query        端口号
     * @param pageNum      当前页码
     * @param pageSize     每页显示条数
     * @return
     */
    PageInfo<IpList> page(String query, int pageNum, int pageSize);

    /**
     * 根据交换机地址 获取IP列表
     *
     * @param swiAddress 交换机ID
     * @return
     */
    List<IpList> getListBySwiAddress(Integer swiAddress);

    /**
     * 根据ID号查看详情
     *
     * @param id id
     * @return 大于0则更新成功
     */
    IpList getById(Integer id);

    /**
     * 更新端口信息
     *
     * @param id id
     * @param ipListParam {@link IpListUpdateParam}
     * @return
     */
    @Transactional
    int update(Integer id, IpListUpdateParam ipListParam);

    /**
     * 更新端口状态
     *
     * @param id id
     * @param status 状态码 0->停用，1->启用
     * @return 大于0则更新成功
     */
    @Transactional
    int updatePortStatus(Integer id, Integer status);

    /**
     * 清空IP信息,数据库信息不删除
     *
     * @param ipList {@link IpList}
     * @return
     */
    @Transactional
    int delete(IpList ipList);

    /**
     * 删除交换机所有IP信息，数据库信息删除
     *
     * @param swiAddress 交换机ID
     * @return
     */
    @Transactional
    int delete(Integer swiAddress);

    /**
     * 创建端口
     *
     * @param ipList {@link IpList}
     * @return
     */
    @Transactional
    int insert(IpList ipList);

}
