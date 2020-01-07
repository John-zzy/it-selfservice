package com.cnec5.it.selfservice.server.controller;

import com.cnec5.it.selfservice.base.BaseSwichDto;
import com.cnec5.it.selfservice.common.dto.CommonPageResult;
import com.cnec5.it.selfservice.common.dto.ResponseResult;
import com.cnec5.it.selfservice.dto.IpListUpdateParam;
import com.cnec5.it.selfservice.dto.IpListsParam;
import com.cnec5.it.selfservice.dto.SwichParam;
import com.cnec5.it.selfservice.provider.api.IpListService;
import com.cnec5.it.selfservice.provider.api.SwiListService;
import com.cnec5.it.selfservice.provider.domain.IpList;
import com.cnec5.it.selfservice.provider.domain.SwiList;
import com.cnec5.it.selfservice.provider.domain.extend.IpListExtend;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 网络管理 Controller
 */
@Api(tags = "NetManagerController", description = "网络管理API")
@RestController
@RequestMapping(value = "netmanager")
public class NetManagerController {

    @Reference(version = "1.0.0")
    private SwiListService swiListService;

    @Reference(version = "1.0.0")
    private IpListService ipListService;

    /**
     * 获取所有IP地址
     *
     * @return
     */
    @ApiOperation(value = "获取所有IP地址")
    @GetMapping(value = "lists")
    public ResponseResult<List<IpList>> iplistAll() {
        List<IpList> ipLists = ipListService.selectAll();
        if (ipLists != null) {
            return new ResponseResult<>(ResponseResult.CodeStatus.SUCCESS, "获取所有IP地址", ipLists);
        }
        else {
            return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "获取所有IP地址失败");
        }
    }

    /**
     * 分页获取IP地址列表
     *
     * @param ipListsParam {@link IpListsParam}
     * @return
     */
    @ApiOperation(value = "分页获取IP地址列表")
    @PostMapping(value = "list")
    public ResponseResult<CommonPageResult<IpList>> pageIpList(@RequestBody IpListsParam ipListsParam) {
        String query = ipListsParam.getQuery();
        int pageNum = ipListsParam.getPageNum();
        int pageSize = ipListsParam.getPageSize();
        CommonPageResult<IpList> result = commonPageResult(query, pageNum, pageSize);
        return new ResponseResult<>(ResponseResult.CodeStatus.SUCCESS, "获取IP地址成功", result);
    }

    /**
     * 获取某交换机的IP地址列表
     *
     * @param swiAddress 交换机地址
     * @return
     */
    @ApiOperation(value = "获取某交换机的IP地址列表")
    @GetMapping(value = "list/{swiAddress}")
    public ResponseResult<CommonPageResult<IpList>> iplist(@PathVariable Integer swiAddress,
                                                           @RequestBody IpListsParam ipListsParam) {
        String query = String.valueOf(swiAddress);
        int pageNum = ipListsParam.getPageNum();
        int pageSize = ipListsParam.getPageSize();
        CommonPageResult<IpList> result = commonPageResult(query, pageNum, pageSize);

        // 请求成功
        if (result != null) {
            return new ResponseResult<>(ResponseResult.CodeStatus.SUCCESS,
                    "获取交换机IP地址列表",
                    result);
        }
        // 请求失败
        else {
            return new ResponseResult<>(ResponseResult.CodeStatus.FAIL,
                    "获取交换机IP地址列表失败");
        }
    }

    /**
     * 获取IP详细信息
     *
     * @param id id
     * @return
     */
    @ApiOperation(value = "获取IP详细信息")
    @GetMapping(value = "list/{id}/detail")
    public ResponseResult<IpListExtend> getById(@PathVariable Integer id) {
        IpListExtend extend = new IpListExtend();
        IpList ipList = ipListService.getById(id);
        // 请求成功
        if (ipList != null) {
            BeanUtils.copyProperties(ipList, extend);
            SwiList swiList = swiListService.get(ipList.getSwiAddress());
            extend.setSwiName(swiList.getSwiName());
            extend.setSwiIpv4(swiList.getSwiIpv4());
            return new ResponseResult<>(ResponseResult.CodeStatus.SUCCESS, "获取IP详细信息", extend);
        }
        // 请求失败
        else {
            return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "获取IP详细信息失败");
        }
    }

    /**
     * 更新IP地址信息
     *
     * @param id id
     * @param param {@link IpListUpdateParam}
     * @return
     */
    @ApiOperation(value = "更新IP地址信息")
    @PutMapping(value = "list/{id}/update")
    public ResponseResult<Void> updateById(@PathVariable Integer id,
                                           @RequestBody IpListUpdateParam param) {
        int result = ipListService.update(id, param);
        // 更新成功
        if (result > 0) {
            return new ResponseResult<>(ResponseResult.CodeStatus.SUCCESS, "IP地址更新成功");
        }
        // 更新失败
        else {
            return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "IP地址更新失败");
        }
    }

    /**
     * 更新IP地址端口状态
     *
     * @param id id
     * @param status 状态码 0->停用，1->启用
     * @return
     */
    @ApiOperation(value = "更新IP地址端口状态")
    @PutMapping(value = "list/{id}/update/{status}")
    public ResponseResult<Void> updatePortStatus(@PathVariable Integer id,
                                                 @PathVariable Integer status) {
        int result = ipListService.updatePortStatus(id, status);
        // 更新成功
        if (result > 0) {
            return new ResponseResult<>(ResponseResult.CodeStatus.SUCCESS, "IP端口状态更新成功");
        }
        // 更新失败
        else {
            return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "IP端口状态更新失败");
        }
    }

    /**
     * IP端口信息删除
     *
     * @param id id
     * @return
     */
    @ApiOperation(value = "IP端口信息删除(数据库不删除交换机端口信息)")
    @PutMapping(value = "list/{id}/delete")
    public ResponseResult<Void> delete(@PathVariable Integer id) {
        // 获取IP信息
        IpList ipList = ipListService.getById(id);
        // 清空信息
        int result = ipListService.delete(ipList);
        // 更新成功
        if (result > 0) {
            return new ResponseResult<>(ResponseResult.CodeStatus.SUCCESS, "IP端口删除成功");
        }
        // 更新失败
        else {
            return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "IP端口删除失败");
        }
    }

    /**
     * 获取交换机父类列表
     *
     * @return
     */
    @ApiOperation(value = "获取交换机父类列表")
    @GetMapping(value = "swilist")
    public ResponseResult<List<SwiList>> swichParent() {
        List<SwiList> swiLists = swiListService.selectSwiLocation();
        // 请求成功
        if (swiLists != null) {
            return new ResponseResult<>(ResponseResult.CodeStatus.SUCCESS, "获取交换机父类列表成功", swiLists);
        }
        // 请求失败
        else {
            return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "获取交换机父类列表");
        }
    }

    /**
     * 获取交换机详细信息
     *
     * @param id 交换机id
     * @return
     */
    @ApiOperation(value = "获取交换机详细信息")
    @GetMapping(value = "switch/{id}")
    public ResponseResult<SwiList> get(@PathVariable Integer id) {
        SwiList swiList = swiListService.get(id);
        // 请求成功
        if (swiList != null) {
            return new ResponseResult<>(ResponseResult.CodeStatus.SUCCESS, "获取交换机详细信息成功", swiList);
        }
        // 请求失败
        else {
            return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "获取交换机详细信息失败");
        }
    }

    /**
     * 更新交换机信息
     *
     * @param id id
     * @param param {@link BaseSwichDto}
     * @return
     */
    @ApiOperation(value = "更新交换机信息")
    @PutMapping(value = "switch/{id}/update")
    public ResponseResult<Void> update(@PathVariable Integer id,
                                       @RequestBody BaseSwichDto param) {
        int result = swiListService.update(id, param);
        // 更新成功
        if (result > 0) {
            return new ResponseResult<>(ResponseResult.CodeStatus.SUCCESS, "更新交换机信息成功");
        }
        // 更新失败
        else {
            return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "更新交换机信息失败");
        }
    }

    /**
     * 删除交换机信息与端口信息
     *
     * @param id id
     * @return
     */
    @ApiOperation(value = "删除交换机信息与端口信息")
    @DeleteMapping(value = "switch/{id}/delete")
    public ResponseResult<Void> deleteSwitch(@PathVariable Integer id) {
        try {
            // 删除交换机所有端口信息
            int result = ipListService.delete(id);

            // 删除成功
            if (result > 0) {
                SwiList swiList = new SwiList();
                swiList.setId(id);
                // 删除交换机信息
                int delete = swiListService.delete(swiList);
                if (delete > 0) {
                    return new ResponseResult<>(ResponseResult.CodeStatus.SUCCESS, "删除成功");
                }
                else {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    throw new SQLException("网络炸啦...");
                }
            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
        }

        return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "删除失败");
    }

    /**
     * 创建交换机
     * (这里涉及了分布式事务的问题，暂时未解决同时成功，或者同时失败)
     *
     * @param swichParam {@link SwichParam}
     * @return
     */
    @ApiOperation(value = "创建交换机")
    @PostMapping(value = "create")
    public ResponseResult<Void> insert(@RequestBody SwichParam swichParam) {
        SwiList swiList = new SwiList();
        // BeanUtils.copyProperties(swichParam, swiList);

        // 拿到parentId -> 父类的ID 和 名称
        int position = Integer.parseInt(swichParam.getPosition());
        String swiLocation = swiListService.get(position).getSwiLocation() + "机房";

        swiList.setParentId(position);
        swiList.setSwiName(swichParam.getSwiName());
        swiList.setSwiIpv4(swichParam.getSwiAddress());
        swiList.setSwiLocation(swiLocation);
        swiList.setSwiComment(swichParam.getSwiComment());

        int result = swiListService.insert(swiList);

        // 创建交换机成功
        if (result > 0) {

            try {
                // 获取创建的交换机的ID
                int id = swiListService.get(swichParam.getSwiAddress()).getId();

                // 创建N个空IP端口
                int portNums = swichParam.getRadio();

                // radio值转换为端口数
                int num = 0;
                if (portNums == 1) num = 48;
                else if (portNums == 2) num = 24;
                else num = 12;

                // 创建端口
                for (int i = 1; i <= num; i++) {
                    IpList ipList = new IpList();
                    ipList.setPortid("");
                    ipList.setSwiAddress(id);
                    ipList.setSwiPortnum(i);
                    ipList.setCreated(new Date());

                    int insertResult = ipListService.insert(ipList);
                    if (insertResult < 0) {
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    }

                }

                return new ResponseResult<>(ResponseResult.CodeStatus.SUCCESS, "交换机创建成功");
            }
            catch (Exception e) {
                e.printStackTrace();
                return new ResponseResult<>(ResponseResult.CodeStatus.SUCCESS, "交换机创建失败");
            }


        }
        // 创建失败
        else {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResponseResult<>(ResponseResult.CodeStatus.SUCCESS, "交换机创建失败");
        }
    }


    /**
     * 获取交换机树形数据
     *
     * @return
     */
    @ApiOperation(value = "获取交换机列表树")
    @GetMapping(value = "switree")
    public ResponseResult<List<SwiList>> treeList() {
        // 查询所有
        List<SwiList> swiLists = swiListService.selectAll();
        List<SwiList> rootLists = Lists.newArrayList();
        List<SwiList> bodyLists = Lists.newArrayList();

        swiLists.forEach(swiList -> {
            // 根节点
            if (swiList.getParentId() == 0) {
                rootLists.add(swiList);
            }
            // 其余节点
            else {
                bodyLists.add(swiList);
            }
        });

        List<SwiList> resultLists = getTree(rootLists, bodyLists);
        return new ResponseResult<List<SwiList>>(ResponseResult.CodeStatus.SUCCESS, "获取交换机列表", resultLists);
    }

    /** 递归方法开始 **/
    private List<SwiList> getTree(List<SwiList> rootLists, List<SwiList> bodyLists) {
        if (bodyLists != null && !bodyLists.isEmpty()) {
            //声明一个map，用来过滤已操作过的数据
            Map<Integer, Integer> map = Maps.newHashMapWithExpectedSize(bodyLists.size());
            rootLists.forEach(beanTree -> getChild(beanTree, map, bodyLists));
            return rootLists;
        }
        return null;
    }

    private void getChild(SwiList treeDto, Map<Integer, Integer> map, List<SwiList> bodyLists) {
        List<SwiList> childList = Lists.newArrayList();
        bodyLists.stream()
                .filter(c -> !map.containsKey(c.getId()))
                .filter(c -> c.getParentId().equals(treeDto.getId()))
                .forEach(c -> {
                    map.put(c.getId(), c.getParentId());
                    getChild(c, map, bodyLists);
                    childList.add(c);
                });
        treeDto.setChildren(childList);//子数据集
    }
    /** 递归方法结束 **/

    private CommonPageResult<IpList> commonPageResult(String query, int pageNum, int pageSize) {
        PageInfo<IpList> pageInfo = ipListService.page(query, pageNum, pageSize);
        return CommonPageResult.pageResult(pageInfo);
    }

}
