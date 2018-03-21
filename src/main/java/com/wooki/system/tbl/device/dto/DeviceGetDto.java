package com.wooki.system.tbl.device.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/1/4
 * Time : 下午11:20
 */
@Data
public class DeviceGetDto {
    @NotNull(message = "id不能为空")
    private Integer id;
}
