package com.cnec5.it.selfservice.provider.service;

import com.cnec5.it.selfservice.dto.IpListUpdateParam;
import com.cnec5.it.selfservice.provider.api.IpListService;
import com.cnec5.it.selfservice.provider.domain.IpList;
import com.cnec5.it.selfservice.provider.mapper.IpListMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * IpListService实现类
 */
@Service(version = "1.0.0")
public class IpListServiceImpl implements IpListService {

    @Resource
    private IpListMapper ipListMapper;

    /**
     * 获取所有IP地址
     *
     * @return 所有IP地址列表
     */
    @Override
    public List<IpList> selectAll() {
        return ipListMapper.selectAll();
    }

    /**
     * 根据查询条件分页获取IP地址列表
     *
     * @param query    端口号\IPV4\交换机ID
     * @param pageNum  当前页码
     * @param pageSize 每页显示条数
     * @return PageInfo
     */
    @Override
    public PageInfo<IpList> page(String query, int pageNum, int pageSize) {
        Example example = new Example(IpList.class);
        Example.Criteria criteria = example.createCriteria();

        if (StringUtils.isNotBlank(query)) {
            criteria.orLike("portid", "%" + query + "%")
                    .orLike("ipv4", "%" + query + "%")
                    .orLike("swiAddress", "%" + query + "%");
        }

        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(ipListMapper.selectByExample(example));
    }

    /**
     * 根据交换机ID获取IP列表
     *
     * @param swiAddress 交换机ID
     * @return IP列表
     */
    @Override
    public List<IpList> getListBySwiAddress(Integer swiAddress) {
        Example example = new Example(IpList.class);
        example.createCriteria().andEqualTo("swiAddress", swiAddress);
        return ipListMapper.selectByExample(example);
    }

    /**
     * 根据ID号查看详情
     *
     * @param id id
     * @return {@link IpList}
     */
    @Override
    public IpList getById(Integer id) {
        Example example = new Example(IpList.class);
        example.createCriteria().andEqualTo("id", id);
        return ipListMapper.selectOneByExample(example);
    }

    /**
     * 更新IP端口信息
     *
     * @param id                id
     * @param ipListUpdateParam {@link IpListUpdateParam}
     * @return 大于0则成功
     */
    @Override
    public int update(Integer id, IpListUpdateParam ipListUpdateParam) {
        IpList oldIpList = getById(id);
        // 更新信息
        oldIpList.setPortid(ipListUpdateParam.getPortid());
        oldIpList.setLocation(ipListUpdateParam.getLocation());
        oldIpList.setFloorNum(ipListUpdateParam.getFloorNum());
        oldIpList.setRoomNum(ipListUpdateParam.getRoomNum());
        oldIpList.setUsername(ipListUpdateParam.getUsername());
        oldIpList.setIpv4(ipListUpdateParam.getIpv4());
        oldIpList.setIpv6(ipListUpdateParam.getIpv6());
        oldIpList.setMac(ipListUpdateParam.getMac());
        oldIpList.setSwiVlan(ipListUpdateParam.getSwiVlan());
        oldIpList.setSwiAccesslist(ipListUpdateParam.getSwiAccesslist());
        oldIpList.setSwiPortnum(ipListUpdateParam.getSwiPortnum());
        oldIpList.setSwiAddress(ipListUpdateParam.getSwiAddress());
        oldIpList.setComment(ipListUpdateParam.getComment());
        oldIpList.setUpdated(new Date());
        return ipListMapper.updateByPrimaryKey(oldIpList);
    }

    /**
     * 更新端口状态
     *
     * @param id     id
     * @param status 状态码 0->停用，1->启用
     * @return 大于0则更新成功
     */
    @Override
    public int updatePortStatus(Integer id, Integer status) {
        // 获取旧IP地址信息
        IpList ipList = getById(id);
        // 更新状态
        ipList.setStatus(status);
        return ipListMapper.updateByPrimaryKey(ipList);
    }

    /**
     * 清空IP信息,数据库信息不删除
     *
     * @param ipList {@link IpList}
     * @return 大于0则成功
     */
    @Override
    public int delete(IpList ipList) {
        ipList.setPortid("");
        ipList.setLocation(null);
        ipList.setFloorNum(null);
        ipList.setRoomNum(null);
        ipList.setUsername(null);
        ipList.setIpv4(null);
        ipList.setIpv6(null);
        ipList.setMac(null);
        ipList.setStatus(0);
        ipList.setSwiVlan(null);
        ipList.setSwiAccesslist(null);
        ipList.setComment(null);
        ipList.setUpdated(new Date());
        return ipListMapper.updateByPrimaryKey(ipList);
    }

    /**
     * 删除交换机所有IP信息，数据库信息删除
     *
     * @param swiAddress 交换机ID
     * @return 大于0则成功
     */
    @Override
    public int delete(Integer swiAddress) {
        Example example = new Example(IpList.class);
        example.createCriteria().andEqualTo("swiAddress", swiAddress);
        return ipListMapper.deleteByExample(example);
    }

    /**
     * 创建端口
     *
     * @param ipList {@link IpList}
     * @return 大于0则成功
     */
    @Override
    public int insert(IpList ipList) {
        return ipListMapper.insert(ipList);
    }


}
