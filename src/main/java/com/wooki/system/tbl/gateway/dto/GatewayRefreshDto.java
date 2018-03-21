package com.wooki.system.tbl.gateway.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/1/21
 * Time : 下午2:39
 */
@Data
public class GatewayRefreshDto {
    @NotNull(message = "网关不能为空")
    private Integer gatewayId;
}
