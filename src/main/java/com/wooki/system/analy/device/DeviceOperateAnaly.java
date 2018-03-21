package com.wooki.system.analy.device;

import com.wooki.function.keypack.SelectKeyPack;
import com.wooki.system.common.GatewayClient;
import com.wooki.system.tbl.device.entity.TblDevice;
import com.wooki.system.tbl.device.service.TblDeviceService;
import com.wooki.system.tbl.report.dto.ReportDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/1/19
 * Time : 上午12:24
 */
@Component
public class DeviceOperateAnaly {


    @Autowired
    TblDeviceService deviceService;

    /**
     * 面板开关操作解析
     * 接收云端返回数据解析
     * @param dataArr   data 数组
     * @param reportDto 反馈的信息
     * @return
     */
    public boolean analy(String[] dataArr, ReportDto reportDto){
        String data = reportDto.getData();
        String newData = GatewayClient.formatString(data);

        // 前面6位固定包头
//        Integer length = Integer.valueOf(dataArr[])   // 数据总长度
        // 从第七位开始解析
        String type = dataArr[6];
        Integer lastLength = Integer.valueOf(dataArr[7]);// 后面数据长度
        String shortAddr = dataArr[9]+dataArr[8]; // 短地址
        String endPoint = dataArr[10];              // 终端
        Integer status = Integer.valueOf(dataArr[dataArr.length-1]);

        TblDevice device = new TblDevice();
        device.setShortAddr(shortAddr);
        device.setEndPoint(endPoint);
        device.setSwitchStatus(status);
        device.setUpdateTime(new Date());
        device.setGatewayId(reportDto.getGid());
        device.setSelectKey(SelectKeyPack.getDeviceSelectKey(device));
        // 这里暂时无返回网关id
//        device.setGatewayId(reportDto.getGatewayId());
        System.out.println(device);

        // 根据短地址，终端号进行更新
//        TblDevice condition = new TblDevice();
//        condition.setShortAddr(shortAddr);
//        condition.setEndPoint(endPoint);
//        deviceService.update(device,new EntityWrapper<>(condition));

        deviceService.updateBySelectKey(device);
        return true;
    }
}
