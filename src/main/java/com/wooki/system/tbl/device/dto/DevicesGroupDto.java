package com.wooki.system.tbl.device.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/1/9
 * Time : 下午12:01
 */
@Data
public class DevicesGroupDto {
    @NotNull(message = "设备id不能为空")
    private List<Integer> ids;
    @NotNull(message = "分组id不能为空")
    private Integer groupId;
}
