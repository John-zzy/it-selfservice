package com.cnec5.it.selfservice.dto;

import com.cnec5.it.selfservice.base.BaseDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 新增用户信息对象
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AddUserParams extends BaseDto {

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "头像")
    private String icon;

}
