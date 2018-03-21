package com.wooki.system.tbl.metting.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/3/2
 * Time : 下午3:15
 */
@Data
public class MettingUnReserveDto {
    @NotNull(message = "预约id不能为空")
    private Long id;
}
