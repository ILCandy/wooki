package com.wooki.system.tbl.company.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/3/2
 * Time : 下午2:55
 */
@Data
public class BSetSuperDto {
    @NotNull(message = "b用户id不能为空")
    private Integer superCompanyId;
    @NotNull(message = "c用户id不能为空")
    private Integer childCompanyId;
}
