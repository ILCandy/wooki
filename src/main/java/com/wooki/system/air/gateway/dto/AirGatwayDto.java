package com.wooki.system.air.gateway.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author wwy
 * @version 1.0
 * @description com.wooki.system.air.gateway.dto
 * @date 2018/3/21
 */
@Data
public class AirGatwayDto {

    @NotNull(message = "id不能为空")
    private String id;

    @NotNull(message = "修改name不能为空")
    private String name;
}
