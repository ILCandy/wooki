package com.wooki.system.tbl.task.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.wooki.system.analy.devicelist.SwitchCommon;
import com.wooki.system.base.BaseJson;
import com.wooki.system.base.Constant;
import com.wooki.system.tbl.bindScene.entity.TblDeviceScene;
import com.wooki.system.tbl.bindScene.service.TblDeviceSceneService;
import com.wooki.system.tbl.device.entity.TblDevice;
import com.wooki.system.tbl.device.service.TblDeviceService;
import com.wooki.system.tbl.task.dto.DeviceTaskDelDto;
import com.wooki.system.tbl.task.dto.DeviceTaskDto;
import com.wooki.system.tbl.task.dto.DevicesTaskDto;
import com.wooki.system.tbl.task.dto.UpdateFrequenceDto;
import com.wooki.system.tbl.task.entity.TblDeviceTask;
import com.wooki.system.tbl.task.entity.TblTask;
import com.wooki.system.tbl.task.service.TblDeviceTaskService;
import com.wooki.system.tbl.task.service.TblTaskService;
import com.wooki.util.common.date.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author whn
 * @since 2018-01-11
 */
@RestController
@RequestMapping("/taskDevice")
public class TblDeviceTaskController {

    private static final Logger logger = LoggerFactory.getLogger(TblDeviceTaskController.class);

    @Autowired
    TblTaskService taskService; // 定时

    @Autowired
    TblDeviceService deviceService; // 设备

    @Autowired
    TblDeviceTaskService deviceTaskService; // 设备定时

    @Autowired
    TblDeviceSceneService deviceSceneService;// 设备绑定情景

    /**
     * 给定时任务添加设备
     * @param deviceTaskDto sceneId 情景id , taskId 定时id，deviceId 设备Id,status 开关
     * @return
     */
    @PostMapping("/deviceAddTask")
    public Object deviceAddTask(@Valid @RequestBody DeviceTaskDto deviceTaskDto){
        // 定时
        TblTask task = taskService.selectById(deviceTaskDto.getTaskId());
        logger.error("定时:"+task);
        // 设备信息
        TblDevice device = deviceService.selectById(deviceTaskDto.getDeviceId());

        // 给设备设置定时
        TblDeviceTask deviceTask ;
        List<TblDeviceTask> deviceTasks = new ArrayList<>();

        // 相关信息
        Integer deviceId = device.getId();
        Integer gatewayId = device.getGatewayId();
        // 按照逗号分隔开
        String[] days = DateUtil.getDaysToArr(task.getDays());

        // data status, 设备状态
        String data = SwitchCommon.deviceAddStatus(device,deviceTaskDto.getStatus());

        // 设备的每日定时
        for(String day:days){
            deviceTask = new TblDeviceTask();
            deviceTask.setDeviceId(deviceId);
            deviceTask.setGatewayId(gatewayId);
            deviceTask.setTaskId(task.getId());
            deviceTask.setDay(Integer.valueOf(day));
            deviceTask.setDeviceId(deviceTaskDto.getDeviceId());
            deviceTask.setEnable(task.getEnable());
            deviceTask.setData(data);
            deviceTasks.add(deviceTask);
        }
//        device.setSceneId(task.getSceneId());
        TblDeviceScene deviceScene = new TblDeviceScene();
        deviceScene.setDeviceId(deviceId);
        deviceScene.setSceneId(task.getSceneId());

        // 给设备添加情景
        // 批量插入设备的定时
        deviceService.deviceAddTask(deviceScene,deviceTasks);
//        deviceSceneService.insert(deviceScene);
        // 给设备添加情景
//        deviceService.updateById(device);
        // 批量插入设备的定时
//        deviceTaskService.insertBatch(deviceTasks);
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS));
    }

    /**
     * 设备添加定时任务
     * @param devicesTaskDto
     * @return
     */
    @PostMapping("/devicesAddTask")
    public Object devicesAddTask(@Valid @RequestBody DevicesTaskDto devicesTaskDto){

        // 定时
        TblTask task = taskService.selectById(devicesTaskDto.getTaskId());

        // 给设备设置定时
        TblDeviceTask deviceTask ;
        List<TblDeviceTask> deviceTasks = new ArrayList<>();

        // 设备的id列表
        List<Integer> ids = devicesTaskDto.getDevicesId();
        // 设备列表
        List<TblDevice> deviceList = deviceService.selectBatchIds(ids);
        // 设备的定时开关状态列表
        List<String> statusList = devicesTaskDto.getStatus();

        if(ids.size()!=(statusList.size()))
            return JSON.toJSON(BaseJson.fail(Constant.OPERATE_FAIL));

        TblDevice device ;
        // 按照逗号分隔开
        String[] days = null;
        if(StringUtils.isNotEmpty(task.getDays()))
            days = task.getDays().split(",");

        // 情景 － 设备列表
        List deviceSceneList = new ArrayList();
        TblDeviceScene deviceScene;
        // 便利设备列表
        for(int i = 0;i<ids.size();i++){
            device = deviceList.get(i);
            deviceScene = new TblDeviceScene();
            // 相关信息
            Integer deviceId = device.getId();
            Integer gatewayId = device.getGatewayId();

            // data status, 设备状态
            String data = SwitchCommon.deviceAddStatus(device,statusList.get(i));

            // 设备对应的定时 ,这里是频率的
            if(days!=null&&days.length>0) {
                // 遍历定时
                for (String day : days) {
                    deviceTask = new TblDeviceTask();
                    deviceTask.setDeviceId(deviceId);
                    deviceTask.setTaskId(task.getId());
                    deviceTask.setGatewayId(gatewayId);
                    deviceTask.setDay(Integer.valueOf(day));
                    deviceTask.setDeviceId(device.getId());
                    deviceTask.setEnable(task.getEnable());
                    deviceTask.setData(data);
                    deviceTasks.add(deviceTask);
                }
            }else{
                // 这里是单次的
                deviceTask = new TblDeviceTask();
                deviceTask.setDeviceId(deviceId);
                deviceTask.setTaskId(task.getId());
                deviceTask.setGatewayId(gatewayId);
                deviceTask.setDeviceId(device.getId());
                deviceTask.setEnable(task.getEnable());
                deviceTask.setData(data);
                deviceTasks.add(deviceTask);
            }
            // 设备设置情景id
            deviceScene.setSceneId(task.getSceneId());
            deviceScene.setDeviceId(device.getId());
            deviceSceneList.add(deviceScene);
        }
        // 更新设备的情景
//        deviceService.updateBatchById(deviceList);
        deviceSceneService.insertBatch(deviceSceneList);
        // 给设备添加定时
        deviceTaskService.insertBatch(deviceTasks);

        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS));
    }

    /**
     * 设备删除定时 deviceId 设备id ， taskId 定时id
     * @param deviceTaskDelDto
     * @return
     */
    @PostMapping("/deviceDelTask")
    public Object deviceDelTask(@Valid @RequestBody DeviceTaskDelDto deviceTaskDelDto) {

        // 定时任务
        TblTask task = taskService.selectById(deviceTaskDelDto.getTaskId());

        // 设备 定时表删除
        TblDeviceTask deviceTask = new TblDeviceTask();
        deviceTask.setDeviceId(deviceTaskDelDto.getDeviceId());
        deviceTask.setTaskId(deviceTaskDelDto.getTaskId());

        // 设备的情景id，删除
        // 情景id 默认0,无分组
        TblDeviceScene deviceScene = new TblDeviceScene();
        deviceScene.setDeviceId(deviceTaskDelDto.getDeviceId());
        deviceScene.setSceneId(task.getSceneId());

//        TblDevice device = deviceService.selectById(deviceTaskDelDto.getDeviceId());
//        device.setSceneId(Constant.INFO_DEFAULT);

        // 这里的定时和情景是一对一的，所以也要把设备的情景id删除 0，是无分组
        // 更新设备表
//        deviceService.updateById(device);
        // 更新情景 － 设备表
        deviceSceneService.delete(new EntityWrapper<>(deviceScene));
        // 更新定时 － 设备表
        deviceTaskService.delete(new EntityWrapper<>(deviceTask));
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS));
    }

    /**
     * 修改定时的频率
     * @param frequenceDto taskId 定时id ,days 频率:Monday,Tuesday,....
     * @return
     */
    @PostMapping("/updateFrequence")
    public Object updateFrequence(@Valid @RequestBody UpdateFrequenceDto frequenceDto) {
        // 原始信息
        TblTask originTask = taskService.selectById(frequenceDto.getTaskId());
        // 1,2,3,4,5,6,7
        String originDays = originTask.getDays();
        // 1,2,3,4,5,6,7
        String days = DateUtil.getDaysToNumStr(frequenceDto.getDays());
        // 接下来比较增加和减少的频率

        // 添加新增的频率

        // 删除减少的频率

        // 定时
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS));
    }

}
