package com.wooki.system.common;

import com.wooki.system.base.Constant;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.List;

public class gateway {

    //    public static int PORT;
//    public static final String SERVER_HOST = "platform1.wookitech.com";
    public static final String SERVER_HOST = "192.168.1.189";
    public static final int PORT = 8001;
    public static final byte GATEWAY_AUTH[] = {0x0b, 0x00, 0x51, 0x00, 0x05, 0x00, 0x31, 0x30, 0x30, 0x30, 0x30, 0x20, 0x71, 0x6c, 0x64, 0x39};

    public static String send( String data) throws IOException {
        String s = data;
        System.out.println("sendMsg : " + data);
        return send(strToByte(data));
    }

    public static byte[] strToByte(String data) {
        data = formatString(data);
        byte[] bs = transfer(data.split(" "));
        return bs;
    }

    public static String formatString(String s) {
        s = s.replaceAll("0(x|X)", ""); //去掉0x或0X字符
        s = s.replaceAll(",", " "); //将逗号替换为空格
        s = s.replaceAll("\\s{1,}", " "); //将连续空格合并为单个空格
        s = s.trim(); //去掉首发动空格
        return s;
    }

    /**
     * 将十六进制字符串转为字节数组
     *
     * @param array
     * @return
     */
    private static byte[] transfer(String array[]) {
        byte bs[] = new byte[array.length];
        for (int i = 0; i < array.length; i++) {
            bs[i] = (byte) Integer.valueOf(array[i], 16).byteValue();
        }

        System.out.println("transfer:");
        for (int i = 0; i < bs.length; i++)
            System.out.print(bs[i]);
        System.out.println();

        return bs;
    }

    public static String send( byte data[]) throws IOException {
        Socket socket = null;
        try {
            socket = new Socket(SERVER_HOST, PORT);

            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            out.write(GATEWAY_AUTH);
            out.flush();

//            byte content[] = new byte[data.length + 5];
//            byte gid[] = gateway(gatewayId);

//            for (int i = 0; i < 5; i++) {
//                content[i] = gid[i];
//            }
//
//            for (int i = 5; i < data.length + 5; i++) {
//                content[i] = data[i - 5];
//            }

            out.write(data);
            out.flush();

            socket.shutdownOutput();

            StringBuffer sb = new StringBuffer();
            while (input.read() != -1) {
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
     *
     * @param gatewayId
     * @return
     */
    private static byte[] gateway(int gatewayId) {
        String gid = String.valueOf(gatewayId);
        byte bs[] = new byte[gid.length()];
        for (int i = 0; i < gid.length(); i++) {
            bs[i] = (byte) gid.charAt(i);
        }
        return bs;
    }

    public static void main(String[] args) throws IOException {
        int gatewayId = 36381;
//        String host = "gateway.wookitech.com";

        // 模拟给设备下达命令
//        byte bs[] = {0x1c, 0x00, 0x0c, 0x00, 0x16, 0x00, 0x16, 0x00, (byte) 0xff, (byte) 0xff, (byte) 0xff,
//                (byte) 0xff, (byte) 0xfe, (byte) 0x82, 0x0d, 0x02, (byte) 0xf2, (byte) 0x89, 0x00, 0x00, 0x00, 0x00, 0x00,
//                0x00, 0x11, 0x00, 0x00, 0x01};

        String a = "16 00 ff ff ff ff fe 82 0d 02 89 f2 00 00 00 00 00 00 10 00 00 01";
        String b = "16 00 ff ff ff ff fe 82 0d 02 89 f2 00 00 00 00 00 00 10 00 00 00";
        String str = Constant.GET_DEVICE_LIST;
        System.out.println(send(a));
        System.out.println(send(b));
        System.out.println(send(str));

//        String b = "1c 00 0c 00 16 00 16 00 ff ff ff ff fe 82 0d 02 89 f2 00 00 00 00 00 00 10 00 00 00";
//        send(gatewayId, b);

        // 模拟设备发送消息给服务器
//        byte bs2[] = {0x0C,0x00,0x0B,0x00,0x06,0x00,0x07,0x04,(byte)0x91,0x56,0x11,0x01};
//        byte bs2[] = {0x0C,0x00,0x0B,0x00,0x06,0x00,0x07,0x04,(byte)0x91,0x56,0x11,0x01};
//        byte[] bs2 = {0x12,0x00,0x0B,0x00,0x0C,0x00 ,0x70,0x0A,(byte)0x91,0x56,0x11,0x06,0x00,0x01,0x00,0x00,0x20,0x00};
//        byte[]bs2 = {0x70,0x08,(byte)0x91,0x56,0x10,0x02,0x04,0x01};
//        byte []bs2 = {(byte)0x0e,0x00,(byte)0x0c,0x00,0x08,0x00,0x08,0x00,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xfe,(byte)0x81};
//        send(gatewayId, bs2);
    }

    // 列表 data 加上状态
//    public static String deviceListAddstatusSendMsg(List deviceList, String status)throws NoSuchMethodException,IOException,InvocationTargetException,IllegalAccessException{
    public static String deviceListAddstatusSendMsg(List deviceList, String status) throws Exception {
        // 统一控制的状态
        String switcha = Constant.map.get(status);
        Class clazz = deviceList.get(0).getClass();
        //
        Method setData = clazz.getDeclaredMethod(Constant.METHOD_SET_DATA, String.class);
        Method getData = clazz.getDeclaredMethod(Constant.METHOD_GET_DATA);
        Object o = null;
        for (int i = 0; i < deviceList.size(); i++) {
            o = deviceList.get(i);
            setData.invoke(o, (String) getData.invoke(o) + switcha);
        }
        return deviceListSendMsg(deviceList);
    }

    //    public static String deviceListSendMsg(List deviceList)throws InvocationTargetException,IllegalAccessException,IOException,NoSuchMethodException{
    public static String deviceListSendMsg(List deviceList) throws Exception {
        Class clazz = deviceList.get(0).getClass();
        Method getGatewayId = clazz.getDeclaredMethod(Constant.METHOD_GET_GATEWAYID);
        Method getData = clazz.getDeclaredMethod(Constant.METHOD_GET_DATA);
        // 此处不用转 ，已经是 00 01 06 16 这种格式

        // 若后期需要改，再加处理
//        return sendList(deviceList,getGatewayId,getData);
        Thread thread;
        for (Object o : deviceList) {
            thread = new SendThread((Integer) getGatewayId.invoke(o), (String) getData.invoke(o));
            thread.start();
        }
        return null;
    }


}