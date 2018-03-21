package com.wooki.system.tbl.lock.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/3/1
 * Time : 下午1:19
 */
@Data
public class LockSetCompanyDto {
    @NotNull(message = "公司id不能为空")
    private Integer companyId;
    @NotNull(message = "门禁id不能为空")
    private Long lockId;
}
