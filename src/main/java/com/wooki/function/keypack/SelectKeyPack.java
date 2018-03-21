package com.wooki.function.keypack;

import com.wooki.system.tbl.device.entity.TblDevice;
import com.wooki.system.tbl.link.entity.TblLink;
import com.wooki.util.common.md5.MD5Util;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/2/9
 * Time : 下午9:21
 */
public class SelectKeyPack {

    // 联动 selectKey
    public static String getLinkSelectKey(TblLink link){
        return getLinkSelectKey(link.getShortAddr(),link.getEndPoint(),link.getStatus());
    }

    public static String getLinkSelectKey(String shortAddr,String endPoint,String status){
        return MD5Util.encode(shortAddr+"+"+endPoint+"+"+status);
    }
    // 联动 selectKey


    // 设备 selectKey
     public static String getDeviceSelectKey(TblDevice device){
         return getDeviceSelectKey(device.getGatewayId(),device.getShortAddr(),device.getEndPoint());
     }

    public static String getDeviceSelectKey(String shortAddr,String endPoint){
//        return MD5Util.encode(gatewayId+"_"+shortAddr+"_"+endPoint);
        return MD5Util.encode(shortAddr+"_"+endPoint);
    }

    public static String getDeviceSelectKey(Integer gatewayId,String shortAddr,String endPoint){
//        return MD5Util.encode(gatewayId+"_"+shortAddr+"_"+endPoint);
        return getDeviceSelectKey(shortAddr,endPoint);
    }
    // 设备 selectKey
}
