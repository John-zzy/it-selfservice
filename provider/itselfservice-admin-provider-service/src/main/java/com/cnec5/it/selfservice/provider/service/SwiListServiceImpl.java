package com.cnec5.it.selfservice.provider.service;

import com.cnec5.it.selfservice.base.BaseSwichDto;
import com.cnec5.it.selfservice.provider.api.SwiListService;
import com.cnec5.it.selfservice.provider.domain.SwiList;
import com.cnec5.it.selfservice.provider.mapper.SwiListMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * SwiListService 实现类
 */
@Service(version = "1.0.0")
public class SwiListServiceImpl implements SwiListService {

    @Resource
    public SwiListMapper swiListMapper;

    /**
     * 查询所有交换机列表
     *
     * @return
     */
    @Override
    public List<SwiList> selectAll() {
        return swiListMapper.selectAll();
    }

    /**
     * 查询所有交换机子集
     *
     * @return
     */
    @Override
    public List<SwiList> selectSwiLocation() {
        Example example = new Example(SwiList.class);
        example.createCriteria().andEqualTo("parentId", 447);
        return swiListMapper.selectByExample(example);
    }

    /**
     * 创建交换机
     *
     * @param swiList {@link SwiList}
     * @return
     */
    @Override
    public int insert(SwiList swiList) {
        return swiListMapper.insert(swiList);
    }

    /**
     * 根据交换机的地址获取信息
     *
     * @param swiIpv4 交换机IPV4地址
     * @return
     */
    @Override
    public SwiList get(String swiIpv4) {
        Example example = new Example(SwiList.class);
        example.createCriteria().andEqualTo("swiIpv4", swiIpv4);
        return swiListMapper.selectOneByExample(example);
    }

    /**
     * 根据ID查询获取交换机信息
     *
     * @param id id
     * @return
     */
    @Override
    public SwiList get(Integer id) {
        Example example = new Example(SwiList.class);
        example.createCriteria().andEqualTo("id", id);
        return swiListMapper.selectOneByExample(example);
    }

    /**
     * 更新交换机信息
     *
     * @param id id
     * @param param {@link BaseSwichDto}
     * @return
     */
    @Override
    public int update(Integer id, BaseSwichDto param) {
        SwiList oldSwiList = get(id);
        oldSwiList.setSwiName(param.getSwiName());
        oldSwiList.setSwiIpv4(param.getSwiIpv4());
        oldSwiList.setSwiLocation(param.getSwiLocation());
        oldSwiList.setSwiComment(param.getSwiComment());
        return swiListMapper.updateByPrimaryKey(oldSwiList);
    }

    /**
     * 删除交换机信息
     *
     * @param swiList {@link SwiList}
     * @return
     */
    @Override
    public int delete(SwiList swiList) {
        return swiListMapper.delete(swiList);
    }
}
