package com.wooki.system.tbl.metting.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/2/28
 * Time : 下午12:56
 */
@Data
public class MettingReserveDto {

    @NotBlank(message = "会议室id不能为空")
    private String id;
    @NotNull(message = "开始时间不能为空")
    private String beginDate;
    @NotNull(message = "结束时间不能为空")
    private String endDate;


}
