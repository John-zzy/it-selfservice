package com.cnec5.it.selfservice.provider.domain.extend;

import com.cnec5.it.selfservice.provider.domain.IpList;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * {@link IpList} 扩展类
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class IpListExtend extends IpList implements Serializable {

    /**
     * 交换机名称
     */
    private String swiName;

    /**
     * 交换机地址
     */
    private String swiIpv4;

}
