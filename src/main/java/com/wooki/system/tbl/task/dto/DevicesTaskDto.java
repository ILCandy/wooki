package com.wooki.system.tbl.task.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/1/11
 * Time : 下午10:35
 */
@Data
public class DevicesTaskDto implements Serializable {
//    @NotNull(message = "情景id不能为空")
//    private Integer sceneId;
    @NotNull(message = "定时id不能为空")
    private Long taskId;
    @NotNull(message = "设备状态不能为空")
    @Size(min = 1)
    private List<String> status;
    @NotNull(message = "设备id不能为空")
    @Size(min = 1)
    private List<Integer> devicesId;
}
