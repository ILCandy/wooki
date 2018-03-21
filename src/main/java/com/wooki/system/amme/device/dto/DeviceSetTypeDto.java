package com.wooki.system.amme.device.dto;

import lombok.Data;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/2/4
 * Time : 下午2:07
 */
@Data
public class DeviceSetTypeDto {
//    @NotBlank(message = "电表id不能为空")
    private String id;
//    @NotNull(message = "类型不能为空")
    private Integer type;
}
