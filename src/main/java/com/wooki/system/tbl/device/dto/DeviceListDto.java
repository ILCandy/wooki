package com.wooki.system.tbl.device.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/1/19
 * Time : 下午5:59
 */
@Data
public class DeviceListDto {
    @NotNull(message = "分组不能为空")
    private Integer groupId;
    @NotBlank(message = "状态不能为空")
    private String status;
}
