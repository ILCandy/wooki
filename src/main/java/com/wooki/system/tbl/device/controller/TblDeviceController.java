package com.wooki.system.tbl.device.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.wooki.function.annotation.BUser;
import com.wooki.system.analy.CompanyInfo;
import com.wooki.system.analy.devicelist.SwitchCommon;
import com.wooki.system.base.BaseJson;
import com.wooki.system.base.Constant;
import com.wooki.system.common.GatewayClient;
import com.wooki.system.tbl.bindGateway.entity.TblBindGateway;
import com.wooki.system.tbl.bindGateway.service.TblBindGatewayService;
import com.wooki.system.tbl.company.entity.TblCompany;
import com.wooki.system.tbl.device.dto.*;
import com.wooki.system.tbl.device.entity.TblDevice;
import com.wooki.system.tbl.device.service.TblDeviceService;
import com.wooki.system.tbl.group.dto.DeviceUpdateDto;
import com.wooki.system.tbl.task.entity.TblDeviceTask;
import com.wooki.system.tbl.task.service.TblDeviceTaskService;
import com.wooki.system.tbl.task.service.TblTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author whn
 * @since 2017-12-31
 */
@RestController
@RequestMapping("/device")
public class TblDeviceController {

    @Autowired
    TblBindGatewayService bindGatewayService;

    @Autowired
    TblDeviceService deviceService;

    @Autowired
    TblTaskService taskService;

    @Autowired
    TblDeviceTaskService deviceTaskService;

    @Autowired
    CompanyInfo companyInfo;

    /**
     * 网页获取设备列表
     * @return
     */
//    @GetMapping("/list")
//    public Object deviceList(HttpServletRequest request){
//        // 根据登陆用户获取
//        TblCompany company = companyInfo.getRedisCompany(request);
//        TblBindGateway tblBindGateway = new TblBindGateway();
//        tblBindGateway.setCompanyId(company.getId());
//        EntityWrapper entityWrapper = new EntityWrapper(tblBindGateway);
//        // 获取所有该公司绑定的网关
//        List<TblBindGateway> gateWayList =  bindGatewayService.selectList(entityWrapper);
//        // 根据网关获取所有设备
//        List<TblDevice> devideList = deviceService.selectDevices(gateWayList);
//        List result = groupDevice(devideList);
//        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS,result));
//    }

    /**
     * 公司对应的设备列表
     * @param request
     * @return
     * @throws Exception
     */
    @BUser
    @GetMapping("/deviceList")
    public Object deviceList(HttpServletRequest request) throws Exception {
        TblCompany company = companyInfo.getRedisCompany(request);
        TblDevice device = new TblDevice();
        device.setCompanyId(company.getId());
        List<TblDevice> deviceList = deviceService.selectList(new EntityWrapper<>(device));
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS,deviceList));
    }
    /**
     * 在线设备数
     *
     * @param request
     * @return
     */
    @BUser
    @GetMapping("/onlineCount")
    public Object onlineCount(HttpServletRequest request) throws Exception{
        Integer count = 0;
        String method = Constant.METHOD_GET_GATEWAYID;
        // 获取公司
        TblCompany company = companyInfo.getRedisCompany(request);
        // 获取所有网关信息
        TblBindGateway tblBindGateway = new TblBindGateway();
        tblBindGateway.setCompanyId(company.getId());
        EntityWrapper entityWrapper = new EntityWrapper(tblBindGateway);
        // 获取所有该公司绑定的网关
        List<TblBindGateway> gateWayList = bindGatewayService.selectList(entityWrapper);

        if (gateWayList != null && gateWayList.size() > 0) {
            // 在线
            TblDevice device = new TblDevice();
            device.setSwitchStatus(Constant.ONLINE_STATUS_ONLINE);
            EntityWrapper deviceEW = new EntityWrapper();
            List ids = getIds(gateWayList, method);
            deviceEW.eq("switch_status", Constant.ONLINE_STATUS_ONLINE)
                    .in("gateway_id", ids);
            count = deviceService.selectCount(deviceEW);
        }
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS, count));
    }

    /**
     * 修改设备，暂时只修改名称
     * @param deviceUpdateDto id，name
     * @return
     */
    @BUser
    @PostMapping("/update")
    public Object updateDevice(@Valid @RequestBody DeviceUpdateDto deviceUpdateDto){
        TblDevice device = new TblDevice(deviceUpdateDto);
        deviceService.updateById(device);
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS));
    }

    //
    private List getIds(List list, String method) throws Exception {
        if (list == null || list.size() == 0)
            return null;
        Class clazz = list.get(0).getClass();
        Method getId = clazz.getDeclaredMethod(method);
        List ids = new ArrayList<>();
        for (Object o : list) {
            ids.add((Integer) getId.invoke(o));
        }
        return ids;
    }

    /**
     * 获取设备的信息以及定时任务
     *
     * @param deviceGetDto
     * @return
     */
    @BUser
    @GetMapping("/get")
    public Object deviceGet(@Valid @RequestBody DeviceGetDto deviceGetDto) {
        TblDevice device = deviceService.selectById(deviceGetDto.getId());
        // 定时任务
        TblDeviceTask deviceTask = new TblDeviceTask(device);
        List<TblDeviceTask> tasks = deviceTaskService.selectList(new EntityWrapper<>(deviceTask));
        device.setTasks(tasks);
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS, device));
    }

    /**
     * 给设备发送消息
     *
     * @param deviceDto id 设备id根据id获取信息 ,status 状态 open,close
     * @return
     * @throws Exception
     */
    @BUser
    @PostMapping("/send")
    public Object send(@Valid @RequestBody DeviceDto deviceDto) throws Exception {
        TblDevice device = deviceService.selectById(deviceDto.getId());
        // 发送消息
        GatewayClient.send(device.getGatewayId(), SwitchCommon.dataAddStatus(device.getData(), deviceDto.getStatus()));
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS));
    }

    /**
     * 给设备设置分组
     *
     * @param deviceGroupDto 设备id，分组 id,groupId
     * @return
     * @throws Exception
     */
    @BUser
    @PostMapping("/setGroup")
    public Object deviceSetGroup(@Valid @RequestBody DeviceGroupDto deviceGroupDto) throws Exception {
        TblDevice device = new TblDevice();
        device.setId(deviceGroupDto.getId());
        device.setGroupId(deviceGroupDto.getGroupId());
        deviceService.updateById(device);
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS));
    }

    @BUser
    @PostMapping("/setDevicesGroup")
    public Object devicesGroup(@Valid @RequestBody DevicesGroupDto devicesGroupDto) throws Exception {
        deviceService.updateBatchDeviceGroup(devicesGroupDto.getGroupId(),devicesGroupDto.getIds());
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS));
    }

    /**
     * 同时控制一个分区里的设备
     * @param deviceListDto
     * @return
     * @throws Exception
     */
    @BUser
    @PostMapping("/sendList")
    public Object sendList(@Valid @RequestBody DeviceListDto deviceListDto)throws Exception{
        EntityWrapper groupDeviceEW = new EntityWrapper();
        groupDeviceEW.eq("group_id",deviceListDto.getGroupId());
        List list = deviceService.selectList(groupDeviceEW);
        GatewayClient.deviceListAddstatusSendMsg(list,deviceListDto.getStatus());
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS));
    }

    /**
     *
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("/linkDevice")
    @BUser
    public Object linkDevice(Page page, HttpServletRequest request)throws Exception{
        TblCompany company = companyInfo.getRedisCompany(request);
        String [] linkType = Constant.LIST_LINK_DEVICE_TYPE.split(",");
        List<String> linkTypeList = new ArrayList<>();
        for(int i = 0;i<linkType.length;i++){
            linkTypeList.add(linkType[i]);
        }
        EntityWrapper linkDeviceEW = new EntityWrapper();
        linkDeviceEW.eq("company_id",company.getId()).in("device_id",linkTypeList);

        page = deviceService.selectPage(page,linkDeviceEW);
        return JSON.toJSON(BaseJson.ok(Constant.OPERATE_SUCCESS,page));
    }

    // 对查询出来的设备进行分组
//    private List groupDevice(List<TblDevice> deviceList) {
//        if (deviceList == null || deviceList.size() <= 0)
//            return null;
//        List<List<TblDevice>> resultList = new ArrayList<>();
//
//        List<TblDevice> groupList = new ArrayList<>();
////        String groupName = deviceList.get(0).getGroupName();
//        Integer groupId = deviceList.get(0).getGroupId();
//        for (TblDevice device : deviceList) {
//            // 是同一组的
//            if (groupId.equals(device.getGroupId())) {
//                groupList.add(device);
//            } else {
//                // 不是同一组，重新创建list
//                resultList.add(groupList);
//                groupList = new ArrayList<>();
//                groupList.add(device);
//                groupId = device.getGroupId();
//            }
//            if (device == deviceList.get(deviceList.size() - 1)) {
//                resultList.add(groupList);
//            }
//        }
//        return resultList;
//    }
}