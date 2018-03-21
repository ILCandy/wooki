package com.wooki.system.air.gateway.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author wwy
 * @version 1.0
 * @description com.wooki.system.air.gateway.dto
 * @date 2018/3/20
 */
@Data
public class IdAirGatwayDto {

    @NotNull(message = "id不能为空")
    private String id;

    @NotNull(message = "公司id不能为空")
    private Integer companyId;
}
