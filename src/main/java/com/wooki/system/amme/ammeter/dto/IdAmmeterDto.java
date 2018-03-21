package com.wooki.system.amme.ammeter.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/1/26
 * Time : 下午11:16
 */
@Data
public class IdAmmeterDto {
    @NotNull(message = "id不能为空")
    private String id;
    @NotNull(message = "公司id不能为空")
    private Integer companyId;
}
