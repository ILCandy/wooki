package com.wooki.system.tbl.link.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/1/24
 * Time : 上午11:11
 */
@Data
public class LinkDeleteDto {
    @NotNull(message = "id不能为空")
    private Integer id;
}
