package com.wooki.system.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.wooki.system.amme.ammeter.entity.AmmeterLogin;
import com.wooki.system.amme.device.entity.AmmeterDevice;
import com.wooki.system.base.Constant;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;

import static com.wooki.system.common.SimpleHttpClientDemo.sendGet;
import static org.apache.el.util.MessageFactory.get;

public class TestGatewayClient {

//    public static final String SERVER_HOST = "gateway.wookitech.com";
public static final String SERVER_HOST = "platform1.wookitech.com";
    public static final int PORT = 8090;
    public static final byte GATEWAY_AUTH[] = {0x0b, 0x00, 0x51, 0x00, 0x05, 0x00, 0x31, 0x30, 0x30, 0x30, 0x30, 0x20, 0x71, 0x6c, 0x64, 0x39};


    public static String send(int gatewayId, String data) throws IOException {
       String s = data;
       return send(gatewayId,strToByte(data));
   }

   public static byte[] strToByte(String data){
       data = formatString(data);
       byte[] bs = transfer(data.split(" "));
       System.out.println(data);
       return bs;
   }

//   public static byte[] adjust(byte[] bs){
//       int sub;
//       for(int i = 0;i<bs.length;i++){
//           byte b = bs[i];
//           if(b<0) {
//               sub = b;
//               sub += 256;
//               bs[i] = (byte)sub;
//           }
//       }
//       return bs;
//   }

   public static String formatString(String s){
       s=s.replaceAll("0(x|X)",""); //去掉0x或0X字符
       s=s.replaceAll(","," "); //将逗号替换为空格
       s=s.replaceAll("\\s{1,}", " "); //将连续空格合并为单个空格
       s=s.trim(); //去掉首发动空格
       return s;
   }

    public static String send(int gatewayId, byte data[]) throws IOException {
        Socket socket = null;
        try {
            socket = new Socket(SERVER_HOST, PORT);

            DataInputStream input = new DataInputStream(socket.getInputStream());

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            out.write(GATEWAY_AUTH);
            out.flush();

            byte content[] = new byte[data.length+5];
            byte gid[] = gateway(gatewayId);

            for(int i=0;i<5;i++) {
                content[i] = gid[i];
            }

            for(int i=5;i<data.length+5;i++){
                content[i] = data[i-5];
            }

            out.write(content);
            out.flush();

            socket.shutdownOutput();

            StringBuffer sb = new StringBuffer();
            while(input.read()!=-1) {
                sb.append(input.readLine());
            }

            out.close();
            input.close();

//            System.out.println(sb.toString());
            return sb.toString();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    socket = null;
                }
            }
        }
    }

    /**
     * 将整形网关ID转为字节数组
     * @param gatewayId
     * @return
     */
    private static byte[] gateway(int gatewayId) {
        String gid = String.valueOf(gatewayId);
        byte bs[] = new byte[gid.length()];
        for(int i=0;i<gid.length();i++) {
            bs[i] = (byte)gid.charAt(i);
        }
        System.out.println();
        return bs;
    }


    /**
     * 将十六进制字符串转为字节数组
     * @param array
     * @return
     */
    private static byte[] transfer(String array[]){
        byte bs[]= new byte[array.length];
        for(int i=0;i< array.length;i++) {
            bs[i] = (byte)Integer.valueOf(array[i],16).byteValue();
        }
        return bs;
    }

    public static void main(String[] args) throws Exception {
        int gatewayId=36381;
//        String host = "gateway.wookitech.com";

        // 模拟给设备下达命令
        byte bs[] = {0x1c, 0x00, 0x0c, 0x00, 0x16, 0x00, 0x16, 0x00, (byte) 0xff, (byte) 0xff, (byte) 0xff,
                (byte) 0xff, (byte) 0xfe, (byte) 0x82, 0x0d, 0x02, (byte) 0xf2,(byte) 0x89, 0x00, 0x00, 0x00, 0x00, 0x00,
                0x00, 0x11, 0x00, 0x00, 0x01};


//        String b = "0x1c 0x00 0x0c 0x00 0x16 0x00 0x16 0x00 0xff 0xff 0xff 0xff 0xfe 0x82 0x0d 0x02 0x89 0xf2 0x00 0x00 0x00 0x00 0x00 0x00 0x12 0x00 0x00 0x00";
//        String b2 = "1c 00 0c 00 16 00 16 00 ff ff ff ff fe 82 0d 02 89 f2 00 00 00 00 00 00 11 00 00 00";
//
//        send(gatewayId, b);
//        send(gatewayId, b2);
//
//        String bb = "0x1c 0x00 0x0c 0x00 0x16 0x00 0x16 0x00 0xff 0xff 0xff 0xff 0xfe 0x82 0x0d 0x02 0x89 0xf2 0x00 0x00 0x00 0x00 0x00 0x00 0x12 0x00 0x00 0x01";
//        String bb2 = "1c 00 0c 00 16 00 16 00 ff ff ff ff fe 82 0d 02 89 f2 00 00 00 00 00 00 11 00 00 01";
//
//        send(gatewayId, bb);
//        send(gatewayId, bb2);

        // 设备列表
//        String str = "0x1c, 0x00,0x0c, 0x00, 0x16, 0x00,0x16, 0x00, 0xff, 0xff, 0xff,0xff,0xfe,0x82,0x0d,0x02,0x89, 0xf2,0x00, 0x00, 0x00, 0x00, 0x00,0x00,0x11,0x00, 0x00,0x01";
        String str = Constant.GET_DEVICE_LIST;
//        for(int i = 0;i<5;i++)
            send(gatewayId,str);

        // 模拟设备发送消息给服务器
//        byte bs2[] = {0x0C,0x00,0x0B,0x00,0x06,0x00,0x07,0x04,(byte)0x91,0x56,0x11,0x01};
//        byte bs2[] = {0x0C,0x00,0x0B,0x00,0x06,0x00,0x07,0x04,(byte)0x91,0x56,0x11,0x01};
//        byte[] bs2 = {0x12,0x00,0x0B,0x00,0x0C,0x00 ,0x70,0x0A,(byte)0x91,0x56,0x11,0x06,0x00,0x01,0x00,0x00,0x20,0x00};
//        byte[]bs2 = {0x70,0x08,(byte)0x91,0x56,0x10,0x02,0x04,0x01};
//        byte []bs2 = {(byte)0x0e,0x00,(byte)0x0c,0x00,0x08,0x00,0x08,0x00,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xfe,(byte)0x81};
//        send(gatewayId, bs2);

        // 电表
//        test4();

//        System.out.println(DateUtil.dealDateFormat("2017-12-11T14:00:36.482+08:00"));
    }

    public static void test4() throws Exception{

        // 电表登陆
        String url = "http://smartammeter.zg118.com:8001/user/login/13027966285/zhoudecai";
        String body = get(url, null, "utf-8");
        System.out.println("交易响应结果：");
        System.out.println(body);

        String data = sendGet(body,null,"utf-8");
        AmmeterLogin loginInfo = getResult(data, AmmeterLogin.class);
        System.out.println(loginInfo);

        //TODO 登陆后获取uid和token备用
        // 获取电表设备
        String url1 = "http://smartammeter.zg118.com:8001/device/ammeter";
        url1 += "?" + "uid=5a28e0ddcb72df2a895d01d0" + "&" + "token="+loginInfo.getToken();
        String body1 = get(url1, null, "utf-8");
        System.out.println("交易响应结果：");
        System.out.println(body1);
        //TODO uuid电表唯一id，把电表数据持久化到数据库

        String deviceData = SimpleHttpClientDemo.sendGet(body1,null,"utf-8");
        System.out.println(deviceData);

        List deviceList = getDeviceListResult(deviceData, AmmeterDevice.class);

    }

    public static <T> T getResult(String result,Class<T> clazz)throws Exception{
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
        HashMap deviceMap = JSON.parseObject(result,HashMap.class);
        JSONArray array = (JSONArray)deviceMap.get(Constant.KEY_DATA);
        // 设备列表
        System.out.println(array);
        System.out.println("=================================================");
        List<T> deviceList = array.toJavaList(clazz);
        AmmeterDevice ammeterDevice;
        for(int i =0;i<deviceList.size();i++) {
            ammeterDevice = (AmmeterDevice)deviceList.get(i);
            System.out.println(ammeterDevice.getExpand());
        }
        return deviceList;
    }
}


