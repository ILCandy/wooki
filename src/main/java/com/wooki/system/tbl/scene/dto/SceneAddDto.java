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
public class SceneAddDto {
    @NotNull(message = "情景名不能为空")
    private String name;
}
