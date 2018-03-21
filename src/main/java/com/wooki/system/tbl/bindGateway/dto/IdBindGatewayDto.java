package com.wooki.system.tbl.bindGateway.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/1/26
 * Time : 上午10:22
 */
@Data
public class IdBindGatewayDto {

    @NotNull(message = "id不能为空")
    private Integer id;
}
