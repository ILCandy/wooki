package com.wooki.system.tbl.bindGateway.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/1/26
 * Time : 上午10:15
 */
@Data
public class BindGayewayDto {
    @NotNull(message = "公司id不能为空")
    private Integer companyId;
    @NotNull(message = "网关id不能为空")
    private Integer gatewayId;
}
