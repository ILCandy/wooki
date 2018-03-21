package com.wooki.system.tbl.company.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/1/26
 * Time : 上午10:25
 */
@Data
public class IdCompanyDto {

    @NotNull(message = "公司id不能为空")
    private Integer id;
}
