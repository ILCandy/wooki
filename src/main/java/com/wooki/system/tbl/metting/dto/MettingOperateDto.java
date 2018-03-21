package com.wooki.system.tbl.metting.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/2/28
 * Time : 下午1:31
 */
@Data
public class MettingOperateDto {
    @NotBlank(message = "会议室id不能为空")
    private String id;
    @NotBlank(message = "密码不能为空")
    private String pwd;
}
