package com.wooki.system.tbl.scene.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/1/12
 * Time : 下午9:59
 */
@Data
public class SceneDelDto {
    @NotNull(message = "情景id不能为空")
    private Integer id;
}
