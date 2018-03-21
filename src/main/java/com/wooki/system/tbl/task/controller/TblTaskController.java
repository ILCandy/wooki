package com.wooki.system.tbl.task.controller;


import com.alibaba.fastjson.JSON;
import com.wooki.system.base.BaseJson;
import com.wooki.system.base.Constant;
import com.wooki.system.tbl.task.dto.TaskAddDto;
import com.wooki.system.tbl.task.dto.TaskDelDto;
import com.wooki.system.tbl.task.dto.TaskUpdateStatusDto;
import com.wooki.system.tbl.task.entity.TblDeviceTask;
import com.wooki.system.tbl.task.entity.TblTask;
import com.wooki.system.tbl.task.service.TblTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author whn
 * @since 2018-01-11
 */
@RestController
@RequestMapping("/task")
public class TblTaskController {

    @Autowired
    TblTaskService taskService;

    /**
     * 情景里添加定时
     * @param taskAddDto 这里的时间必须 为 dateStr:2017-11-01 9:00:00
     * @return
     */
    @PostMapping("/add")
    public Object addTask(@Valid @RequestBody TaskAddDto taskAddDto){
        TblTask task = new TblTask(taskAddDto);
        taskService.insert(task);
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS,task));
    }

    /**
     * 启用定时
     * @param taskUpdateStatusDto  id,status
     * @return
     */
    @PostMapping("/updateStatus")
    public Object updateTaskStatus(@Valid @RequestBody TaskUpdateStatusDto taskUpdateStatusDto){
        // 任务
        TblTask task = new TblTask(taskUpdateStatusDto);
        TblDeviceTask deviceTask = new TblDeviceTask(taskUpdateStatusDto);
        taskService.updateStatus(task,deviceTask);
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS));
    }

    /**
     * 删除定时
     * @param taskDelDto taskId：任务id
     * @return
     */
    @PostMapping("/delete")
    public Object delTask(@Valid @RequestBody TaskDelDto taskDelDto){
        // 删除的定时任务
        TblTask task = new TblTask(taskDelDto);
        // 要删除的定时－设备表
        TblDeviceTask deviceTask = new TblDeviceTask(taskDelDto);
        taskService.deleteTask(task,deviceTask);
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS));
    }
}
