package com.wooki.system.tbl.task.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/1/13
 * Time : 下午2:15
 */
@Data
public class TaskDelDto {
   @NotNull(message = "定时id不能为空")
    private Long taskId;
}
