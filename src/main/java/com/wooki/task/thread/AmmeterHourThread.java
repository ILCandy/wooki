package com.wooki.task.thread;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.wooki.system.amme.device.entity.AmmeterDevice;
import com.wooki.system.amme.expand.entity.DeviceExpand;
import com.wooki.system.amme.expand.service.DeviceExpandService;
import com.wooki.system.amme.info.entity.AmmeterHourInfo;
import com.wooki.system.common.AmmeterClient;
import com.wooki.system.common.ApplicationContextUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/1/27
 * Time : 下午3:53
 */
public class AmmeterHourThread extends Thread{

    private DeviceExpandService deviceExpandService;

    @Override
    public void run(){
        try {
            this.deviceExpandService = (DeviceExpandService) ApplicationContextUtil.getBean(DeviceExpandService.class);
            System.out.println("开启线程计算电表信息" + Thread.currentThread().getName());
            calcuInfo();
            System.out.println("电表信息计算完毕" + Thread.currentThread().getName());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean calcuInfo()throws Exception{
        // 电表列表
        List<AmmeterDevice> deviceList =  AmmeterClient.ammeterLogin();
        // 电表的电量相关信息  uuid 关联
        // 当前获取到的电表电量数据
        List<DeviceExpand> expandList = getExpandList(deviceList);

        // 获取上一次的数据
        List<DeviceExpand> originExpandList = deviceExpandService.selectList(new EntityWrapper<>());

        // 计算此次的数据跟上一次的差值
        // expandList - originExpandList ->resultList
        List<AmmeterHourInfo> resultList = calcuHourInfoList(expandList,originExpandList);
        deviceExpandService.updateAmmeterInfo(deviceList,expandList,resultList);
        return true;
    }

    private List<AmmeterHourInfo> calcuHourInfoList(List<DeviceExpand> expandList,List<DeviceExpand> originExpandList){
        // 如果现在的数据没有
        if(expandList==null||expandList.size()<=0)
            return null;
        List<AmmeterHourInfo> resultList = new ArrayList<>();

        // 有原始数据，并且有最新数据
        DeviceExpand expand;
        DeviceExpand originExpand;
        AmmeterHourInfo hourInfo;

        Date date = new Date();

        // 如果原始数据没有，则是直接插入
        if(originExpandList==null||originExpandList.size()<=0){
            for(int i = 0;i<expandList.size();i++){
                expand = expandList.get(i);
                hourInfo = new AmmeterHourInfo();
                hourInfo.setUuid(expand.getUuid());
                hourInfo.setAllpower(expand.getAllpower());
                hourInfo.setTime(date);
                resultList.add(hourInfo);
            }
        }else{
            for(int i = 0;i<expandList.size();i++){
                expand = expandList.get(i);
                for(int j = 0;j<originExpandList.size();j++){
                    originExpand = originExpandList.get(i);
                    // 如果uuid匹配
                    if(expand.getUuid().equals(originExpand.getUuid())){
                        hourInfo = new AmmeterHourInfo();
                        hourInfo.setUuid(expand.getUuid());
                        hourInfo.setTime(date);
                        hourInfo.setAllpower(expand.getAllpower()-originExpand.getAllpower());
                        resultList.add(hourInfo);
                        break;
                    }
                }
            }
        }
        return resultList;
    }



//    private List<>
    /**
     * 获取电表的电量列表
     * @param deviceList
     * @return
     */
    private List<DeviceExpand> getExpandList(List<AmmeterDevice> deviceList){
        if(deviceList==null||deviceList.size()<=0)
            return null;
        List<DeviceExpand> expandList = new ArrayList<>();
        for(AmmeterDevice device:deviceList)
            expandList.add(device.getExpand());
        return expandList;
    }
}
