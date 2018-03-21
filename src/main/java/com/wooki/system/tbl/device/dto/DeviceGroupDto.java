package com.wooki.system.tbl.device.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/1/9
 * Time : 下午12:01
 */
@Data
public class DeviceGroupDto {
    @NotNull(message = "设备id不能为空")
    private Integer id;
    @NotNull(message = "分组id不能为空")
    private Integer groupId;
}
