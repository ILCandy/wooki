package com.wooki.system.analy.devicelist;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.wooki.function.keypack.SelectKeyPack;
import com.wooki.system.base.Constant;
import com.wooki.system.tbl.bindGateway.entity.TblBindGateway;
import com.wooki.system.tbl.bindGateway.service.TblBindGatewayService;
import com.wooki.system.tbl.company.service.TblCompanyService;
import com.wooki.system.tbl.device.entity.TblDevice;
import com.wooki.system.tbl.device.service.TblDeviceService;
import com.wooki.system.tbl.report.dto.ReportDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.wooki.system.analy.devicelist.SwitchCommon.transferDeviceData;


/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/1/19
 * Time : 上午12:22
 */
@Component
public class DeviceListAnaly {

    @Autowired
    TblDeviceService deviceService;

    @Autowired
    TblBindGatewayService bindGatewayService;

    @Autowired
    TblCompanyService companyService;

    /**
     *
     * @param dataArr  分割后的数据，防止多次分割操作
     * @param reportDto 反馈的信息
     * @return
     */
    public boolean analy(String[] dataArr, ReportDto reportDto){
        String data = reportDto.getData();
        Integer gatewayId = reportDto.getGid();
        // 将data解析成设备列表
        List<String> deviceList = getDeviceFromStringDate(dataArr,data);
        List<TblDevice> tblDeviceList = new ArrayList<>();
        TblDevice tblDevice = null;

        TblBindGateway bindGateway = getComanyId(reportDto.getGid());
        // 一条数据是一个设备
        for(String str :deviceList){
            // 分析每个设备
            tblDevice = analyOneDevice(str);
            tblDevice.setCompanyId(bindGateway.getCompanyId());
            tblDevice.setGatewayId(gatewayId);
//            tblDevice.setSelectKey(SelectKeyPack.getDeviceSelectKey(tblDevice));
            // 拼接好data后返回
            tblDeviceList.add(transferDeviceData(tblDevice));
        }

        // 根据网关删除
        TblDevice delDevice = new TblDevice();
        delDevice.setGatewayId(gatewayId);

        deviceService.refreshDevice(delDevice,tblDeviceList);
        return true;
    }

    private TblBindGateway getComanyId(Integer gatewayId) {
        TblBindGateway bindGateway = new TblBindGateway();
        bindGateway.setGatewayId(gatewayId);
        bindGateway = bindGatewayService.selectOne(new EntityWrapper<>(bindGateway));
        return bindGateway;
    }
    private TblDevice analyOneDevice(String str){
        String[] strArr = str.split(" ");
        Integer index = 0;
        // 设备
        TblDevice tblDevice = new TblDevice();
        // 数据总长
        Integer allLength = Integer.valueOf(strArr[index+1]+strArr[index++],16);
        index++;
        // 数据类型
        Integer dataType = Integer.valueOf(strArr[index+1]+strArr[index++],16);
        index++;
        // 内容数据长度
        Integer dataLength = Integer.valueOf(strArr[index+1]+strArr[index++],16);
        index++;
        // 固定返回 0x01
        String response = strArr[index++];
        // 后续数据长度
        Integer followLength = Integer.valueOf(strArr[index++],16);
        // 设备短地址
        String shortAddr = strArr[index+1]+strArr[index++];
        index++;
        // 终端
        String endPoint = strArr[index++];
        // profile_id
        String profileId = strArr[index+1]+strArr[index++];
        index++;
        // 设备 device_id,设备的大类类型
        String deviceId = strArr[index+1]+strArr[index++];
        index++;
        // 开关状态
        Integer status = Integer.valueOf(strArr[index++],16);
        // 名字长度
        Integer nameLength = Integer.valueOf(strArr[index++],16);
        // 名字
        StringBuffer sb = new StringBuffer();
        for(int i = 0;i<nameLength;i++){
            sb.append(strArr[index++]);
            if(i<nameLength-1)
                sb.append(" ");
        }
        String name = sb.toString();
        // 后续的下标起始
//            Integer index = 17+nameLength;
        // 在线状态
        Integer onlineStatus = Integer.valueOf(strArr[index++],16);
        // ieee ,固定8字节
        StringBuffer ieeeBuffer = new StringBuffer();
        for(int i = 0; i< Constant.IEEE_LENGTH; i++){
            ieeeBuffer.append(strArr[index++]);
        }
        // ieee
        String ieee = ieeeBuffer.toString();
        // sn长度
        Integer snLength = Integer.valueOf(strArr[index++],16);
        // sn码
        StringBuffer snBuffer = new StringBuffer();
        for(int i = 0;i<snLength;i++){
            snBuffer.append(strArr[index++]);
//                if(i<snLength-1)
//                    snBuffer.append(" ");
        }
        // sn 码
        String sn = snBuffer.toString();
        // 大类下面的小类
        String zoneType = strArr[index+1]+strArr[index++];
        // 最后三位暂时未确定
        String lasta = strArr[index++];
        String lastb = strArr[index++];
        String lastc = strArr[index++];

        tblDevice.setShortAddr(shortAddr);
        tblDevice.setEndPoint(endPoint);
        tblDevice.setProfileId(profileId);
        tblDevice.setDeviceId(deviceId);
        tblDevice.setIeee(ieee);
        tblDevice.setName(name);
        tblDevice.setZoneType(zoneType);
        tblDevice.setSwitchStatus(status);
        tblDevice.setSn(sn);
        tblDevice.setOnline(onlineStatus);
        tblDevice.setSelectKey(SelectKeyPack.getDeviceSelectKey(tblDevice));
        return tblDevice;
    }

    // 获取列表，一条数据里可能包含几个设备
    public static List<String> getDeviceFromStringDate(String[] dataArr,String data){
        List deviceList = new ArrayList();
        // 同一个网关一般来说，一条数据里的多个设备的数据长度是一样的，所以均分
        // 单条的长度
        Integer oneSize = Integer.valueOf(dataArr[0],16);

        StringBuffer stringBuffer = null;
        // 多条设备
        for(int i = 0;i<dataArr.length/oneSize;i++){
            stringBuffer = new StringBuffer();
            for(int j = i*oneSize;j<(i+1)*oneSize;j++){
                stringBuffer.append(dataArr[j]);
                if(j<(i+1)*oneSize-1)
                    stringBuffer.append(" ");
            }
            deviceList.add(stringBuffer.toString());
            System.out.println(stringBuffer.toString());
        }
        return deviceList;
    }
}
