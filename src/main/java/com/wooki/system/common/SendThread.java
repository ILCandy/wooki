package com.wooki.system.common;

/**
 * Created by Intellij IDEA
 * User : Whn
 * Date : 18/1/21
 * Time : 下午1:14
 */
public class SendThread extends Thread {

    private Integer gatewayId;
    private String data;

    SendThread(){}

    SendThread(Integer gatewayId,String data){
        this.gatewayId = gatewayId;
        this.data = data;
    }
    @Override
    public void run() {
        try {
            System.out.println("thread run =========================");
            GatewayClient.send(gatewayId, data);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
