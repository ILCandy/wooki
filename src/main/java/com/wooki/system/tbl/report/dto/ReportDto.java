package com.wooki.system.tbl.report.dto;

import lombok.Data;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/1/14
 * Time : 上午11:27
 */
@Data
public class ReportDto {
//    @NotNull(message = "网关id不能为空")
    private Integer gid;
//    @NotNull(message = "fd不能为空")
    private Integer fd;
//    @NotNull(message = "data不能为空")
    private String data;

}
