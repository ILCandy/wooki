package com.wooki.system.analy.report;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.wooki.function.keypack.SelectKeyPack;
import com.wooki.system.base.Constant;
import com.wooki.system.tbl.device.entity.TblDevice;
import com.wooki.system.tbl.device.service.TblDeviceService;
import com.wooki.system.tbl.link.entity.TblLink;
import com.wooki.system.tbl.link.service.TblLinkService;
import com.wooki.system.tbl.report.dto.ReportDto;
import com.wooki.system.thread.DeviceSendThread;
import com.wooki.util.common.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/1/24
 * Time : 下午4:58
 * 设备主动上报信息
 */
@Component
public class DeviceReportAnaly {

    @Autowired
    TblLinkService linkService;

    @Autowired
    TblDeviceService deviceService;

    public boolean analy(String[] dataArr,ReportDto reportDto)throws Exception{
        // 网关id
        Integer gatewayId = reportDto.getGid();
        // 前六位是包头
        // 第七位开始解析
        int index = 6;
        String type = dataArr[index++];
        Integer length = Integer.valueOf(dataArr[index++],16);// 后续数据x长度，16进制
        String shortAddr = dataArr[index+1]+dataArr[index++];// 短地址
        index++;
//        Integer endPoint = Integer.valueOf(dataArr[index++],16); // 终端
        String endPoint = dataArr[index++]; // 终端
        String deviceId = dataArr[index+1] + dataArr[index++] ; // deviceId
        index++;
        Integer reportNum = Integer.valueOf(dataArr[index++],16);// 报告树木吗，暂定16进制解析
        String attribId = dataArr[index+1] + dataArr[index++]; // ???? ，属性id，温度，湿度 等
        index++;
        String dataType = dataArr[index++];
        // data数据
        StringBuffer dataInfo = new StringBuffer();
        for(;index<dataArr.length;index++){
            dataInfo.append(dataArr[index]);
            if(index<dataArr.length-1)
                dataInfo.append(" ");
        }
        String data = dataInfo.toString();
        String status = dataArr[index-2]+dataArr[index-1];
        System.out.println("传感器状态:"+status);
        System.out.println("终端号:"+endPoint);
        System.out.println("deviceId:"+deviceId);
        // 设备类型和状态判断
        if(Constant.LIST_LINK_DEVICE_TYPE.contains(deviceId))
            opera(gatewayId,shortAddr,endPoint,status);
        return true;
    }

    // 总的执行
    private void opera(Integer gatewayId,String shortAddr,String endPoint,String status)throws Exception{
        System.out.println("opera=======================");
        String linkSelectKey = SelectKeyPack.getLinkSelectKey(shortAddr,endPoint,status);
        EntityWrapper linkEW = new EntityWrapper();
        linkEW.eq("select_key",linkSelectKey);
//        linkEW.eq("short_addr",shortAddr)
//              .eq("end_point",endPoint)
//                .eq("status",status);
        // 联动信息
        TblLink link = linkService.selectOne(linkEW);

        // 设备详情
//        String deviceSelectKey = SelectKeyPack.getDeviceSelectKey(gatewayId,shortAddr,endPoint);
        String deviceSelectKey = SelectKeyPack.getDeviceSelectKey(shortAddr,endPoint);
        EntityWrapper deviceEW = new EntityWrapper();
        deviceEW.eq("select_key",deviceSelectKey);
        TblDevice device = deviceService.selectOne(deviceEW);

        System.out.println("link ===== "+link);
        System.out.println("device ===== "+device);
        // 联动信息或者设备信息不存在
        if(link==null||device==null)
            return ;
        // 属于触发联动的设备类型列表里和 在指定时间内,并且状态相同
        System.out.println("status :"+status);
        System.out.println("link status:"+link.getStatus());
        if(checkDays(link) && status.equals(link.getStatus()) && checkTime(link.getTime())) {
            System.out.println("执行操作");
            new DeviceSendThread(getDeviceList(link.getDeviceStatus())).start();
//            GatewayClient.deviceListSendMsg()
        }else{
            System.out.println("不执行操作");
        }
    }

    private static boolean checkDays(TblLink link){
        String[]days = link.getDays().split(",");
        String day = DateUtil.getDay().toString();
        for(String dayInDays:days)
            if(day.equals(dayInDays)) {
                System.out.println("day true ====");
                return true;
            }
        return false;
    }

    // 检查设备是否在联动类型里
    private boolean checkType(String type){
        System.out.println("info_type : "+Constant.LIST_LINK_DEVICE_TYPE);
        System.out.println("type = "+type);
        if(Constant.LIST_LINK_DEVICE_TYPE.contains(type)) {
            System.out.println("type true ====");
            return true;
        }
        return false;
    }

    // 判断时间
    private boolean checkTime(String linkTime){
        String[] timeArr = linkTime.split(",");
        Long startTime;
        Long endTime;
        Long nowDayTime = DateUtil.nowDayTime();
        for(int i = 0;i<timeArr.length;i++){
            String[] tArr = timeArr[i].split(Constant.TIMESPLITSTR);
            startTime = Long.valueOf(tArr[0]);
            endTime = Long.valueOf(tArr[1]);

            System.out.println("==================");
            System.out.println("start time:"+startTime);
            System.out.println("end time:"+endTime);
            System.out.println("nowDayTime: "+nowDayTime);

            // 正负时间差30秒
            if(nowDayTime>(startTime-Constant.TIME_DIFFERENCE) && (endTime+Constant.TIME_DIFFERENCE) >nowDayTime) {
                System.out.println("time true =============");
                return true;
            }
        }
        return false;
    }

    // deviceList
    private List<TblDevice> getDeviceList(String deviceStatus){
        // 解析 deviceStatus
        ConcurrentHashMap map = getDeviceIdsAndStatus(deviceStatus);
        List<TblDevice> deviceList = deviceService.selectBatchIds((List)map.get("ids"));
        System.out.println("deviceList :"+deviceList);
//        System.out.println("getDeviceList : "+appendDeviceData(deviceList,(ConcurrentHashMap)map.get("childMap")));
        return appendDeviceData(deviceList,(ConcurrentHashMap)map.get("childMap"));
    }

    // 拼接数据
    private List<TblDevice> appendDeviceData(List<TblDevice> deviceList,ConcurrentHashMap childMap){
        TblDevice device = null;
        System.out.println("map :"+childMap);
        for(int i = 0;i<deviceList.size();i++){
            device = deviceList.get(i);
            device.setData(device.getData()+childMap.get(device.getId()));
        }
        System.out.println("appendDeviceData = "+deviceList);
        return deviceList;
    }

    // 根据deviceStatus 获取id列表,id对应的status，存进map
    private ConcurrentHashMap<String,List> getDeviceIdsAndStatus(String deviceStatus){
        ConcurrentHashMap map = new ConcurrentHashMap();
        ConcurrentHashMap childMap = new ConcurrentHashMap();

        String[] deviceArr = deviceStatus.split(",");
        List<Integer> ids = new ArrayList<>();
        List<String> statusList = new ArrayList<>();

        Integer id;
        String status;
        String[] arr;
        for(int i=0;i<deviceArr.length;i++){
            // 分割
            arr = deviceArr[i].split(":");
            id = Integer.valueOf(arr[0]);
            status = arr[1];
            // ids
            ids.add(id);
            // childMap
            childMap.put(id,status);
        }
        map.put("ids",ids);
        map.put("statusList",statusList);
        map.put("childMap",childMap);
        return map;
    }

}
