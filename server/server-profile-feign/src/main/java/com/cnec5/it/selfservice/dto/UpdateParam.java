package com.cnec5.it.selfservice.dto;

import com.cnec5.it.selfservice.base.BaseDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 更新用户信息对象
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UpdateParam extends BaseDto {

    /**
     * 状态码
     */
    @ApiModelProperty(value = "状态码")
    private int status;

}
