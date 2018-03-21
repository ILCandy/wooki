package com.wooki.system.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.wooki.system.amme.ammeter.entity.AmmeterLogin;
import com.wooki.system.amme.device.entity.AmmeterDevice;
import com.wooki.system.base.Constant;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

import static com.wooki.system.common.SimpleHttpClientDemo.sendGet;
import static org.apache.el.util.MessageFactory.get;


/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/1/26
 * Time : 下午11:57
 */
public class AmmeterClient {


    public static void main(String[]args)throws Exception{
        ammeterLogin();
    }
    /**
     * 电表登录
     * @throws Exception
     */
    public static List ammeterLogin() throws Exception{
        // 电表登陆
        String url = "http://smartammeter.zg118.com:8001/user/login/13027966285/zhoudecai";
        String body = get(url, null, "utf-8");
        System.out.println("交易响应结果：");
        System.out.println(body);

        String data = sendGet(body,null,"utf-8");
        AmmeterLogin loginInfo = getLoginResult(data, AmmeterLogin.class);
        System.out.println(loginInfo);
        return ammeterDevice(loginInfo);
    }

    /**
     * 获取登录设备
     * @param loginInfo
     * @throws Exception
     */
    public static List ammeterDevice(AmmeterLogin loginInfo) throws Exception{
        // 登陆后获取uid和token备用
        // 获取电表设备
        String url1 = "http://smartammeter.zg118.com:8001/device/ammeter";
        url1 += "?" + "uid=5a28e0ddcb72df2a895d01d0" + "&" + "token="+loginInfo.getToken();
        String body1 = get(url1, null, "utf-8");
        System.out.println("交易响应结果：");
        System.out.println(body1);

        String deviceData = sendGet(body1,null,"utf-8");
        System.out.println(deviceData);

        List deviceList = getDeviceListResult(deviceData, AmmeterDevice.class);
        return deviceList;
    }

    public static <T> T getLoginResult(String result, Class<T> clazz)throws Exception{
        System.out.println(result);
        HashMap infoMap = JSON.parseObject(result,HashMap.class);
        String o = JSON.toJSONString(infoMap.get("Data"));
        String token = (String)infoMap.get(Constant.KEY_EXPAND);
        T analyResult = JSON.parseObject(o,clazz);
        Method method = clazz.getDeclaredMethod(Constant.METHOD_SET_TOKEN,String.class);
        method.invoke(analyResult,token);
        System.out.println("analyResult :"+analyResult);
        return analyResult;
    }

    /**
     * 设备总的电量更新
     * @param result
     * @param clazz
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> List<T> getDeviceListResult(String result, Class<T> clazz)throws Exception{
        System.out.println("array==========================================");
//        ConcurrentHashMap deviceMap = JSON.parseObject(result,ConcurrentHashMap.class);
        HashMap deviceMap = JSON.parseObject(result,HashMap.class);
        JSONArray array = (JSONArray)deviceMap.get(Constant.KEY_DATA);
        // 设备列表
        System.out.println(array);
        System.out.println("=================================================");
        List<T> deviceList = array.toJavaList(clazz);
        AmmeterDevice ammeterDevice;
        for(int i =0;i<deviceList.size();i++) {
            ammeterDevice = (AmmeterDevice)deviceList.get(i);
            // uuid
            ammeterDevice.getExpand().setUuid(ammeterDevice.getUuid());
            System.out.println(ammeterDevice.getExpand());
        }
        return deviceList;
    }
}
