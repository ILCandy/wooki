package com.wooki.system.thread;

import com.wooki.system.common.GatewayClient;

import java.util.List;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/2/1
 * Time : 上午11:53
 */
public class DeviceSendThread extends Thread {

    // 设备列表
    private List list;
    public DeviceSendThread(List list){
        this.list = list;
    }

    @Override
    public void run() {
        try {
            GatewayClient.deviceListSendMsg(list);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

