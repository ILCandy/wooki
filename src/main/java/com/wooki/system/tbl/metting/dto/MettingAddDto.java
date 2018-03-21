package com.wooki.system.tbl.metting.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/2/23
 * Time : 下午1:26
 */
@Data
public class MettingAddDto {
    @NotNull(message = "会议名称不能为空")
    private String name;
}
