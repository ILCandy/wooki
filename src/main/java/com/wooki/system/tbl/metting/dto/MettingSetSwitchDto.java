package com.wooki.system.tbl.metting.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/2/23
 * Time : 下午2:07
 */
@Data
public class MettingSetSwitchDto {
    @NotBlank(message = "会议室id不能为空")
    private String id;
    @NotNull(message = "设备id不能为空")
    private Integer switchId;
}
