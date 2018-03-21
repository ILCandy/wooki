package com.wooki.system.analy.devicelist;

import com.wooki.system.base.Constant;
import com.wooki.system.tbl.device.entity.TblDevice;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/1/4
 * Time : 下午10:23
 * 解析／ 拼接 开关 的data
 */
public class SwitchCommon {

    // 此处不用再添加0x ，逗号等，直接拼接成  01 07
    // 这里加开关状态，定时任务需要
    @Deprecated
    static String switchSendStatusMsg(TblDevice device,String status){
        String switcha = Constant.map.get(status);
        StringBuffer data = getData(device);
        // 开关状态
        data.append(switcha);
        return data.toString();
    }

    // 拼接上控制的状态  ，设备的data中无状态，定时的时候需要添加
    public static String deviceAddStatus(TblDevice device,String status){
        return dataAddStatus(device.getData(),status);
    }

    // 数据加上 控制状态
    public static String dataAddStatus(String data,String status){
        return data + Constant.map.get(status);
    }

    // 获取设备的时候，拼接的data， 这里的data无保存状态
    static TblDevice transferDeviceData(TblDevice device){
        device.setData(getData(device).toString());
        return device;
    }

    // 获取设备的时候，拼接的data， 这里的data无保存状态
    private static StringBuffer getData(TblDevice device){
        StringBuffer sb = new StringBuffer();
        // /*协议头 6字节
        sb.append("1c 00 "); // 数据总长
        sb.append("0c 00 16 00 ");//
        sb.append("16 00 ");//
        //  协议头 */
        StringBuffer snBuffer = new StringBuffer();
        for(int i = device.getSn().length()/2-1 ; i>=0 ; i--){
            snBuffer.append(device.getSn().substring(i*2,(i+1)*2)+" ");
        }
        sb.append("ff ff ff ff ");// 网关SN 号，不是设备
        sb.append("fe ");//控制标志
        sb.append("82 ");//控制类型
        sb.append("0d ");//后续长度
        sb.append("02 ");//地址模式
        String shortAddr = device.getShortAddr();
        sb.append(shortAddr.substring(2,4)+" "+shortAddr.substring(0,2)+" ");// 短地址倒转
        sb.append("00 00 00 00 00 00 ");//保留数据
        sb.append(device.getEndPoint()+" "); // 终端
        sb.append("00 00 ");// 保留数据

        System.out.println("data :"+sb.toString());
        return sb;
    }
}
