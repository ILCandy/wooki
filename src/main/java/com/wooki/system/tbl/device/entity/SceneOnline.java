package com.wooki.system.tbl.device.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Data;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/1/15
 * Time : 下午3:13
 */
@Data
public class SceneOnline {
    @TableField("scene_id")
    private Integer sceneId;
    private Integer count;
}
