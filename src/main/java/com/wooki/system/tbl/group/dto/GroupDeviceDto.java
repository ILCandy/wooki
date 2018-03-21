package com.wooki.system.tbl.group.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/1/15
 * Time : 下午2:29
 */
@Data
public class GroupDeviceDto {
    @NotNull(message = "分组id不能为空")
    private Integer groupId;
}
