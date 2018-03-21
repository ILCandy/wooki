package com.wooki.system.tbl.link.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/1/24
 * Time : 上午10:47
 */
@Data
public class LinkAddDto {

    @NotNull(message = "设备id不能为空")
    private Integer deviceId;
    @NotNull(message = "状态不能为空")
    private String status;
    @NotBlank(message = "时间不能为空")
    private String time;
    @NotNull(message = "联动设备不能为空")
    private String linkList;
    @NotNull(message = "联动频率不能为空")
    private String days;
}
