package com.wooki.system.tbl.scene.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.wooki.system.analy.CompanyInfo;
import com.wooki.system.base.BaseJson;
import com.wooki.system.base.Constant;
import com.wooki.system.tbl.bindScene.entity.TblDeviceScene;
import com.wooki.system.tbl.bindScene.service.TblDeviceSceneService;
import com.wooki.system.tbl.company.entity.TblCompany;
import com.wooki.system.tbl.device.entity.SceneOnline;
import com.wooki.system.tbl.device.entity.TblDevice;
import com.wooki.system.tbl.device.service.TblDeviceService;
import com.wooki.system.tbl.scene.dto.SceneAddDto;
import com.wooki.system.tbl.scene.dto.SceneDelDto;
import com.wooki.system.tbl.scene.dto.SceneUpdateDto;
import com.wooki.system.tbl.scene.entity.TblScene;
import com.wooki.system.tbl.scene.service.TblSceneService;
import com.wooki.system.tbl.task.entity.TblDeviceTask;
import com.wooki.system.tbl.task.entity.TblTask;
import com.wooki.system.tbl.task.service.TblDeviceTaskService;
import com.wooki.system.tbl.task.service.TblTaskService;
import com.wooki.system.thread.DeviceSendThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author whn
 * @since 2018-01-12
 */
@RestController
@RequestMapping("/scene")
public class TblSceneController {

    @Autowired
    TblSceneService sceneService;

    @Autowired
    TblDeviceService deviceService;

    @Autowired
    TblTaskService taskService;

    @Autowired
    TblDeviceTaskService deviceTaskService;

    @Autowired
    TblDeviceSceneService deviceSceneService;

    @Autowired
    CompanyInfo companyInfo;

    /**
     * 场景列表
     * @param request
     * @return
     */
    @RequestMapping("/list")
    public Object sceneList(HttpServletRequest request){
        TblCompany company = companyInfo.getRedisCompany(request);
        TblScene scene = new TblScene();
        scene.setCompanyId(company.getId());
        // 所属公司的情景列表
        List sceneList = sceneService.selectList(new EntityWrapper<>(scene));
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS,sceneList));
    }

    /**
     * 情景的在线数
     * @return
     */
    @GetMapping("/onlineCount")
    public Object onlineCount(HttpServletRequest request){
        TblCompany company = companyInfo.getRedisCompany(request);

        // 情景列表
        TblScene scene = new TblScene();
        scene.setCompanyId(company.getId());
        List<TblScene> scenes = sceneService.selectList(new EntityWrapper<>(scene));

        // 分组查出来的数据
        TblDevice device = new TblDevice();
        device.setCompanyId(company.getId());
        device.setOnline(Constant.ONLINE_STATUS_ONLINE);

        ////// ===================

//        List onlineList = deviceService.selectSceneOnline(device)

        // 先把设备列表提取出来
        List sceneIds = getSceneIds(scenes);
        EntityWrapper deviceSceneEW = new EntityWrapper();
        deviceSceneEW.in("scene_id",sceneIds).orderBy("scene_id");
        List<TblDeviceScene> deviceSceneList = deviceSceneService.selectList(deviceSceneEW);

        // id 列表
        List deviceIds = getIds(deviceSceneList);
        if(deviceIds==null)
            return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS));

        // 吧设备提取出来
//        EntityWrapper deviceEW = new EntityWrapper();
//        deviceEW.in("id",deviceIds)
//                .eq("online",Constant.ONLINE_STATUS_ONLINE);
        // 在线的设备数
//        List<TblDevice> deviceList = deviceService.selectList(deviceEW);
//        List onlineList = transOnlineScene(sceneIds,deviceSceneList,deviceList);
        List onlineList = deviceService.selectSceneOnline(device,scenes);
        ////// ===================

        onlineList = transScene(scenes,onlineList);

        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS,onlineList));
    }

//    private List transOnlineScene(List<Integer> sceneIds,List<TblDeviceScene> deviceScenes,List<TblDevice> deviceList){
//
//        Map map = new ConcurrentHashMap();
//        // 情景：情景在线数
//        for(Integer id:sceneIds)
//            map.put(id,0);
//
//
//        List onlineSceneList = new ArrayList();
//        SceneOnline sceneOnline;
//        Integer sceneId;
//        Integer count;
//        for(int i = 0;i<deviceScenes.size();i++){
//            sceneOnline = new SceneOnline();
//            sceneId = deviceScenes.get(i).getSceneId();
//            count = 0;
//            // 现在的设备列表，已经是在线的列表了
//            for(int j = 0;j<deviceList.size();j++){
////                if()
//            }
//            sceneOnline.setCount(count);
//            sceneOnline.setSceneId(sceneId);
//            onlineSceneList.add(sceneOnline);
//        }
//        return onlineSceneList;
//    }

    // 获取设备 id 列表
    private List getSceneIds(List<TblScene> sceneList){
        if(sceneList==null||sceneList.size()<=0)
            return null;
        List sceneIds = new ArrayList();
        for(TblScene scene:sceneList){
            sceneIds.add(scene.getId());
        }
        return sceneIds;
    }

    // 获取设备 id 列表
    private List getIds(List<TblDeviceScene> deviceSceneList){
        if(deviceSceneList==null||deviceSceneList.size()<=0)
            return null;
        List deviceIds = new ArrayList();
        for(TblDeviceScene deviceScene:deviceSceneList){
            deviceIds.add(deviceScene.getDeviceId());
        }
        return deviceIds;
    }

    // 设备中无的设置为0
    private List transScene(List<TblScene> scenes, List<SceneOnline> sceneOnlines){
        if(scenes==null||scenes.size()==0)
            return sceneOnlines;
        SceneOnline sceneOnline ;
        TblScene scene;
        boolean flag;
        for(int i = 0;i<scenes.size();i++){
            scene = scenes.get(i);
            flag = false;
            for(int j = 0;j<sceneOnlines.size();j++){
                if(scene.getId().equals(sceneOnlines.get(j).getSceneId())){
                    flag = true;
                    break;
                }
            }
            // 如果分组中没有
            if(!flag){
                sceneOnline = new SceneOnline();
                sceneOnline.setSceneId(scene.getId());
                sceneOnline.setCount(0);
                sceneOnlines.add(sceneOnline);
            }
        }
        return sceneOnlines;
    }

    /**
     * 添加场景
     * @param request name 场景名
     * @return
     */
    @PostMapping("/add")
    public Object addScene(HttpServletRequest request, @Valid @RequestBody SceneAddDto sceneAddDto){
        TblCompany company = companyInfo.getRedisCompany(request);
        TblScene scene = new TblScene();
        scene.setName(sceneAddDto.getName());
        scene.setCompanyId(company.getId());
        scene.setAddTime(new Date());
        sceneService.insert(scene);
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS,scene));
    }

    /**
     * 立即执行
     * @param request
     * @param sceneDelDto ，id，情景id
     * @return
     */
    @PostMapping("/executeNow")
    public Object executeNow(HttpServletRequest request, @Valid @RequestBody SceneDelDto sceneDelDto)throws Exception{
        TblTask selectTask = new TblTask();
        selectTask.setSceneId(sceneDelDto.getId());
        // 此处的情景和定时是一对一
        selectTask = taskService.selectOne(new EntityWrapper<>(selectTask));
        if(selectTask==null)
            throw new RuntimeException("该情景下无定时任务");
        // 定时－设备表
        TblDeviceTask deviceTask = new TblDeviceTask();
        deviceTask.setTaskId(selectTask.getId());
        // 要执行操作的设备
        List<TblDeviceTask> deviceTasks = deviceTaskService.selectList(new EntityWrapper<>(deviceTask));
        if(deviceTasks==null||deviceTasks.size()<=0)
            throw new RuntimeException("无操作的设备");

        // 立即执行，是执行的定时器操作
//        GatewayClient.deviceListSendMsg(deviceTasks);
        new DeviceSendThread(deviceTasks).start();
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS));
    }

    /**
     * 更新场景
     * @param request
     * @param sceneUpdateDto
     * @return
     */
    @PostMapping("/update")
    public Object updateScene(HttpServletRequest request, @Valid @RequestBody SceneUpdateDto sceneUpdateDto){
        TblCompany company = companyInfo.getRedisCompany(request);
        TblScene scene = new TblScene();
        scene.setId(sceneUpdateDto.getId());
        scene.setName(sceneUpdateDto.getName());
        scene.setAddTime(new Date());
        sceneService.updateById(scene);
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS,scene));
    }

    /**
     * 删除情景
     * @param request
     * @param sceneDelDto 情景id
     * @return
     */
    @PostMapping("/delete")
    public Object delScene(HttpServletRequest request,@Valid @RequestBody SceneDelDto sceneDelDto){

        TblScene scene = new TblScene();
        scene.setId(sceneDelDto.getId());

        // 设备的情景id设为空
//        TblDevice device = new TblDevice();
//        device.setSceneId(Constant.INFO_DEFAULT);
//        TblDevice checkDevice = new TblDevice();
//        checkDevice.setSceneId(scene.getId());

        TblDeviceScene tblDeviceScene = new TblDeviceScene();
        tblDeviceScene.setSceneId(sceneDelDto.getId());

//        deviceSceneService.delete(new EntityWrapper<>(tblDeviceScene));

        // 获取情景的定时
        TblTask task = new TblTask();
        task.setSceneId(scene.getId());
        task = taskService.selectOne(new EntityWrapper<>(task));

        TblDeviceTask deviceTask = null;
        // 定时信息
        if(task!=null){
            deviceTask = new TblDeviceTask();
            deviceTask.setTaskId(task.getId());
        }
        // 删除情景 － 设备关系
        // 删除定时 －设备关系
        // 删除定时
        sceneService.deleteScene(new EntityWrapper<>(tblDeviceScene),task,deviceTask,scene);
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS));
    }

    /**
     * 情景下的设备列表
     * @param request
     * @param sceneDelDto
     * @return
     */
    @PostMapping("/detailInfo")
    public Object deviceList(HttpServletRequest request,@Valid @RequestBody SceneDelDto sceneDelDto){
        // 设备
//        EntityWrapper deviceEw = new EntityWrapper<>();
//        deviceEw.eq("scene_id",sceneDelDto.getId());
//        List deviceList = deviceService.selectList(deviceEw);

        //情景
        TblScene scene = sceneService.selectById(sceneDelDto.getId());

        // 定时
        EntityWrapper taskEW = new EntityWrapper();
        taskEW.eq("scene_id",sceneDelDto.getId());
        TblTask task = taskService.selectOne(taskEW);

        // 在同个情景下的设备
        List<TblDeviceScene> deviceSceneList = deviceSceneService.selectList(taskEW);
        List ids = new ArrayList();
        for(int i =0;i<deviceSceneList.size();i++){
            ids.add(deviceSceneList.get(i).getDeviceId());
        }
        List deviceList = new ArrayList();
        if(ids!=null&&ids.size()>0)
            deviceList = deviceService.selectBatchIds(ids);

        HashMap map = new HashMap();
        map.put("deviceList",deviceList);
        map.put("scene",scene);
        map.put("task",task);
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS,map));
    }


}
