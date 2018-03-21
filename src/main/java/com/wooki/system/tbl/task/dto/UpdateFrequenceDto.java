package com.wooki.system.tbl.task.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/1/11
 * Time : 下午11:56
 */
@Data
public class UpdateFrequenceDto {
    @NotNull(message = "定时id不能为空")
    private Long taskId;
//    @NotNull(message = "频率不能为空")
    private String days;
}
