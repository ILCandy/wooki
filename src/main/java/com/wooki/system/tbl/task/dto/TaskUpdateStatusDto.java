package com.wooki.system.tbl.task.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/1/20
 * Time : 上午10:25
 */
@Data
public class TaskUpdateStatusDto {
    @NotNull(message = "定时id不能为空")
    private Long id;
    @NotBlank(message = "状态不能为空")
    private String status;
}
