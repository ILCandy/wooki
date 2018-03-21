package com.wooki.system.amme.ammeter.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/2/3
 * Time : 下午2:29
 */
@Data
public class IdAmDeviceDto {
    @NotBlank(message = "id不能为空")
    private String id;
}
