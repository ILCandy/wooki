package com.wooki.system.tbl.task.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/1/13
 * Time : 下午2:15
 */
@Data
public class TaskAddDto {
    @NotNull(message = "情景id不能为空")
    private Integer sceneId;
    @NotNull(message = "定时名不能为空")
    private String name;
    @NotBlank(message = "时间不能为空")
    private String dateStr;
    @NotNull(message = "频率不能为空")
    private Integer frequency; // 频率，0，单次， 1频率
    @NotNull(message = "是否开启定时不能为空")
    private Integer enable;
    private String days;
}
