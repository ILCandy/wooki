package com.wooki.system.common;

import com.wooki.system.base.Constant;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

@Component
public class GatewayClient {

//    public static String SERVER_HOST;
//    public static int PORT;
    public static final String SERVER_HOST = "platform.wookitech.com";
    public static final int PORT = 8090;
    public static final byte GATEWAY_AUTH[] = {0x0b, 0x00, 0x51, 0x00, 0x05, 0x00, 0x31, 0x30, 0x30, 0x30, 0x30, 0x20, 0x71, 0x6c, 0x64, 0x39};


//    @Value("${cloud.server.host}")
//    public void setHost(String host) {
//        SERVER_HOST = host;
//    }
//
//    @Value("${cloud.server.port}")
//    public void setP(Integer port) {
//        PORT = port;
//    }

    public static String send(final int gatewayId, String... ss) throws IOException, InterruptedException {
        List<byte[]> list = new ArrayList<byte[]>();

        for (String d : ss) {
            String s = d;
            s = s.replaceAll("0(x|X)", ""); //去掉0x或0X字符
            s = s.replaceAll(",", " "); //将逗号替换为空格
            s = s.replaceAll("\\s{1,}", " "); //将连续空格合并为单个空格
            s = s.trim(); //去掉首尾空格

            System.out.println("s = "+s);
            byte[] a = transfer(s.split(" "));
            list.add(a);
        }

        return send(gatewayId, list);
    }
//    public static String send(final int gatewayId, String... ss) throws IOException, InterruptedException {
//        String[] s = ss;
//        for(int i =0;i<ss.length;i++)
//            System.out.println("sendMsg : " + s[i]);
//        return send(gatewayId, strToByte(s));
//    }

    public static List<byte[]> strToByte(String[] ss) {
        List<byte[]> list = new ArrayList<>();
        String data;
        for(int i = 0;i<ss.length;i++) {
            data = formatString(ss[i]);
            byte[] bs = transfer(data.split(" "));
            list.add(bs);
        }
        return list;
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

    public static String send(int gatewayId, List<byte[]> list) throws IOException, InterruptedException {
        Socket socket = null;
        try {
            socket = new Socket(SERVER_HOST, PORT);
            socket.setTcpNoDelay(true);
            socket.setOOBInline(true);

            //DataInputStream input = new DataInputStream(socket.getInputStream());
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            sendData(socket, GATEWAY_AUTH);

            int gid_len = String.valueOf(gatewayId).length();
            byte gid[] = gateway(gatewayId);

            for (byte[] data : list) {
                byte content[] = new byte[data.length + gid_len];
                for (int i = 0; i < gid_len; i++) {
                    content[i] = gid[i];
                }

                for (int i = gid_len; i < data.length + gid_len; i++) {
                    content[i] = data[i - gid_len];
                }


                sendData(socket, content);
            }


            socket.shutdownOutput();


            String tmp;
            String s = null;
            while ((tmp = input.readLine()) != null) {
                s = tmp;
            }

            input.close();

            //System.out.println(s);
            System.out.println("OK");

            return s;
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

    private static boolean sendData(Socket socket, byte data[]) {
        try {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.write(data);
            out.flush();
            /*
            解决TCP粘包问题
            https://my.oschina.net/andylucc/blog/625315
            http://jerrypeng.me/2013/08/mythical-40ms-delay-and-tcp-nodelay/
            */
            Thread.sleep(40);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
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

    public static void main(String[] args) throws IOException,InterruptedException {
        int gatewayId = 36381;
//        String host = "gateway.wookitech.com";

        // 模拟给设备下达命令
        byte bs[] = {0x1c, 0x00, 0x0c, 0x00, 0x16, 0x00, 0x16, 0x00, (byte) 0xff, (byte) 0xff, (byte) 0xff,
                (byte) 0xff, (byte) 0xfe, (byte) 0x82, 0x0d, 0x02, (byte) 0xf2, (byte) 0x89, 0x00, 0x00, 0x00, 0x00, 0x00,
                0x00, 0x11, 0x00, 0x00, 0x01};
//        String b = "1c 00 0c 00 16 00 16 00 ff ff ff ff fe 82 0d 02 89 f2 00 00 00 00 00 00 10 00 00 00";
//        send(gatewayId, b);

//        String b = "1c 00 0c 00 16 00 16 00 ff ff ff ff fe 82 0d 02 89 f2 00 00 00 00 00 00 10 00 00 01";
//        send(gatewayId,b);

        // 模拟设备发送消息给服务器
//        byte bs2[] = {0x0C,0x00,0x0B,0x00,0x06,0x00,0x07,0x04,(byte)0x91,0x56,0x11,0x01};
//        byte bs2[] = {0x0C,0x00,0x0B,0x00,0x06,0x00,0x07,0x04,(byte)0x91,0x56,0x11,0x01};
//        byte[] bs2 = {0x12,0x00,0x0B,0x00,0x0C,0x00 ,0x70,0x0A,(byte)0x91,0x56,0x11,0x06,0x00,0x01,0x00,0x00,0x20,0x00};
//        byte[]bs2 = {0x70,0x08,(byte)0x91,0x56,0x10,0x02,0x04,0x01};
//        byte []bs2 = {(byte)0x0e,0x00,(byte)0x0c,0x00,0x08,0x00,0x08,0x00,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xfe,(byte)0x81};
//        send(gatewayId, bs2);

//        test111();
//        for(int i = 0;i<10;i++) {
//        String str = Constant.GET_DEVICE_LIST;
//        String str = "1c 00 0c 00 16 00 16 00 ff ff ff ff fe 82 0d 02 89 F2 00 00 00 00 00 00 12 00 00 ";
//        send(gatewayId, str+"01");
//        boolean flag = false;
//        for(int i = 0;i<5;i++) {
//            if(flag)
//                send(gatewayId, str+"01");
//            else
//                send(gatewayId, str+"00");
//            flag = !flag;
//        }
//        for(int i = 0;i<5;i++)
//            System.out.println(send(gatewayId, str));
//        }
//        for(int i = 0;i<5;i++)
//            send(gatewayId,Constant.GET_DEVICE_LIST);

//        test111();
//        test1();
        test2();
    }

    static void test111()throws IOException,InterruptedException {
        boolean flag = false;
        for(int i = 0;i<1;i++) {
            if(flag)
                test1();
            else
                test2();
            flag = !flag;
        }
    }

    private static void test1()throws IOException,InterruptedException {
        int gatewayId = 36381;
        String s1 = "1c 00 0c 00 16 00 16 00 ff ff ff ff fe 82 0d 02 89 f2 00 00 00 00 00 00 10 00 00 01";
        String s2 = "1c 00 0c 00 16 00 16 00 ff ff ff ff fe 82 0d 02 89 f2 00 00 00 00 00 00 11 00 00 01";
        String s3 = "1c 00 0c 00 16 00 16 00 ff ff ff ff fe 82 0d 02 89 f2 00 00 00 00 00 00 12 00 00 01";
        send(gatewayId, s1,s2,s3);
//        send(gatewayId, s2);
//        send(gatewayId, s3);
    }

    private static void test2()throws IOException,InterruptedException {
        int gatewayId = 36381;
        String s1 = "1c 00 0c 00 16 00 16 00 ff ff ff ff fe 82 0d 02 89 f2 00 00 00 00 00 00 10 00 00 00";
        String s2 = "1c 00 0c 00 16 00 16 00 ff ff ff ff fe 82 0d 02 89 f2 00 00 00 00 00 00 11 00 00 00";
        String s3 = "1c 00 0c 00 16 00 16 00 ff ff ff ff fe 82 0d 02 89 f2 00 00 00 00 00 00 12 00 00 00";
        send(gatewayId, s1);
        send(gatewayId, s2);
        send(gatewayId, s3);
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

    public static String deviceListSendMsg(List deviceList) throws Exception {

        System.out.println("发送联动的设备数:"+deviceList.size());
        for(int i = 0;i<deviceList.size();i++) {
            System.out.println(deviceList.get(i));
        }
        Class clazz = deviceList.get(0).getClass();
        Method getGatewayId = clazz.getDeclaredMethod(Constant.METHOD_GET_GATEWAYID);
        Method getData = clazz.getDeclaredMethod(Constant.METHOD_GET_DATA);
        // 此处不用转 ，已经是 00 01 06 16 这种格式

        // 若后期需要改，再加处理
//        return sendList(deviceList,getGatewayId,getData);
//        Integer gatewayId =

//        for (Object o : deviceList) {
//            thread = new SendThread((Integer) getGatewayId.invoke(deviceList), (String) getData.invoke(o));
//            thread.start();
//        }
//        String data ;
        Object o = null;
        Integer gatewayId ;
        String data ;
        // 定时和立即执行都有效，唯独联动的时候，发送数据卡住了，导致无响应
        for(int i =0;i<deviceList.size();i++){
            o = deviceList.get(i);
            gatewayId = (Integer) getGatewayId.invoke(o);
            data = (String) getData.invoke(o);
            System.out.println("gatewayId = "+gatewayId);
            System.out.println("data ="+data);
            send(gatewayId,data);
        }
        return null;
    }
}