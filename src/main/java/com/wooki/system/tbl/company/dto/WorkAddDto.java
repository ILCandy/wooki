package com.wooki.system.tbl.company.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/1/13
 * Time : 上午11:18
 */
@Data
public class WorkAddDto {

    @NotNull(message = "上班时间不能为空")
    private String workTime;// 下班时间
    @NotNull(message = "午休时间不能为空")
    private String middayRest;// 午休时间
    @NotNull(message = "下班时间不能为空")
    private String comeOffWork;// 下班时间
    @NotNull(message = "工作时间列表")
    @Size(min = 1,max = 2)
    List<String> workDays;
    private Double electricCharge;

}
