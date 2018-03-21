package com.wooki.system.tbl.link.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/1/24
 * Time : 上午11:16
 */
@Data
public class LinkUpdateDto {
    @NotNull(message = "id不能为空")
    private Integer id;
    private String time;
    private String deviceStatus;
}
