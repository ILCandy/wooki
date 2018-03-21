package com.wooki.system.tbl.metting.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/3/19
 * Time : 下午8:30
 */
@Data
public class MettingSetBUserDto {
    @NotNull(message = "会议室id不能为空")
    private Long mettingId;
    @NotNull(message = "公司id不能为空")
    private Integer companyId;
}
