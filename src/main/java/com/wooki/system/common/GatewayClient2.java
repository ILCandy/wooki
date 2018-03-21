package com.wooki.system.common;


import com.wooki.system.base.Constant;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class GatewayClient2 {

//    public static final String SERVER_HOST = "gateway.wookitech.com";
    public static final String SERVER_HOST = "platform1.wookitech.com";
    public static final int PORT = 8090;
    public static final byte GATEWAY_AUTH[] = {0x0b, 0x00, 0x51, 0x00, 0x05, 0x00, 0x31, 0x30, 0x30, 0x30, 0x30, 0x20, 0x71, 0x6c, 0x64, 0x39};


    public static String send(final int gatewayId, String... ss) throws IOException, InterruptedException {
        List<byte[]> list = new ArrayList<byte[]>();

        for (String d : ss) {
            String s = d;
            s = s.replaceAll("0(x|X)", ""); //去掉0x或0X字符
            s = s.replaceAll(",", " "); //将逗号替换为空格
            s = s.replaceAll("\\s{1,}", " "); //将连续空格合并为单个空格
            s = s.trim(); //去掉首尾空格

            byte[] a = transfer(s.split(" "));
            list.add(a);
        }

        return send(gatewayId, list);
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
        return bs;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        int gatewayId = 36381;
        byte bs[] = {0x0e, 0x00, 0x0c, 0x00, 0x08, 0x00, 0x08, 0x00, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xfe, (byte) 0x9d};

        String s = "0x0e,0x00,0x0c,0x00,0x08,0x00,0x08,0x00,0xff,   0Xff,0xff,0xff,0xfe,0x9d";
        String str = Constant.GET_DEVICE_LIST;
        System.out.println(send(gatewayId,str));

//        send(gatewayId, bs);
//        send(gatewayId, s);
//        send(gatewayId, s);


        //     send(gatewayId, s1);
        //     send(gatewayId, s2);
        //     send(gatewayId, s3);

//        int flag = 0;
////        for (int i = 0; i < 10; i++) {
//
////            String s1 = "0x1c,0x00,0x0c,0x00,0x16,0x00,0x16,0x00,0xff,0xff,0xff,0xff,0xfe,0x82,0x0d,0x02,0xe8,0xdd,00,00,00,00,00,00,10,00,00,0" + flag;
//            String s1 = "1c 00 0c 00 16 00 16 00 ff ff ff ff fe 82 0d 02 89 f2 00 00 00 00 00 00 10 00 00 00";
////            String s2 = "0x1c,0x00,0x0c,0x00,0x16,0x00,0x16,0x00,0xff,0xff,0xff,0xff,0xfe,0x82,0x0d,0x02,0xe8,0xdd,00,00,00,00,00,00,11,00,00,0" + flag;
//            String s2 = "1c 00 0c 00 16 00 16 00 ff ff ff ff fe 82 0d 02 89 f2 00 00 00 00 00 00 11 00 00 00";
////            String s3 = "0x1c,0x00,0x0c,0x00,0x16,0x00,0x16,0x00,0xff,0xff,0xff,0xff,0xfe,0x82,0x0d,0x02,0xe8,0xdd,00,00,00,00,00,00,12,00,00,0" + flag;
//            String s3 = "1c 00 0c 00 16 00 16 00 ff ff ff ff fe 82 0d 02 89 f2 00 00 00 00 00 00 12 00 00 00";
////            System.out.println();
//            send(gatewayId, s1);
//            send(gatewayId, s2);
//            send(gatewayId, s3);
//            sleep(1000);
//            flag = flag == 0 ? 1 : 0;
////        }

//        test1();
        test2();
    }

    static void test1()throws IOException, InterruptedException {
        int gatewayId = 36381;
        String s1 = "1c 00 0c 00 16 00 16 00 ff ff ff ff fe 82 0d 02 89 f2 00 00 00 00 00 00 10 00 00 00";
        String s2 = "1c 00 0c 00 16 00 16 00 ff ff ff ff fe 82 0d 02 89 f2 00 00 00 00 00 00 11 00 00 00";
        String s3 = "1c 00 0c 00 16 00 16 00 ff ff ff ff fe 82 0d 02 89 f2 00 00 00 00 00 00 12 00 00 00";
        send(gatewayId, s1);
        send(gatewayId, s2);
        send(gatewayId, s3);
    }

    static void test2()throws IOException, InterruptedException {
        int gatewayId = 36381;

        String s1 = "1c 00 0c 00 16 00 16 00 ff ff ff ff fe 82 0d 02 89 f2 00 00 00 00 00 00 10 00 00 01";
        String s2 = "1c 00 0c 00 16 00 16 00 ff ff ff ff fe 82 0d 02 89 f2 00 00 00 00 00 00 11 00 00 01";
        String s3 = "1c 00 0c 00 16 00 16 00 ff ff ff ff fe 82 0d 02 89 f2 00 00 00 00 00 00 12 00 00 01";
        send(gatewayId, s1);
        send(gatewayId, s2);
        send(gatewayId, s3);
    }
}