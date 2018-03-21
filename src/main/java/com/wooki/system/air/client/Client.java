package com.wooki.system.air.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/** 
 * @author wwy
 * @since 1.0 
 */  
public class Client {  
  
    public static void main(String[] args) throws UnknownHostException, IOException {  
        String serverIp = "192.168.1.24";  
        int port = 8899;  
        Client client = new Client(serverIp,port);  
        client.start();  
    }  
      
    private String serverIp;  
    private int port;  
    private Socket socket;  
    private boolean running=false;  
    private long lastSendTime;  
      
    public Client(String serverIp, int port) {  
        this.serverIp=serverIp;this.port=port;  
    }  
      
    public void start() throws UnknownHostException, IOException {  
        if(running)return;  
        socket = new Socket(serverIp,port);  
        System.out.println("本地端口"+socket.getLocalPort());
        lastSendTime=System.currentTimeMillis();  
        running=true;  
        new Thread(new KeepAliveWatchDog()).start();  
        new Thread(new ReceiveWatchDog()).start();  
    }  
      
    public void stop(){  
        if(running)running=false;  
    }  
      
    public void sendObject(byte[] data) throws IOException {  
    	DataOutputStream oos = new DataOutputStream(socket.getOutputStream());
    	oos.write(data);
    	System.out.println("发送维持连接包"+bytesToHex(data, 0, data.length));
        oos.flush();  
    }  
      
    class KeepAliveWatchDog implements Runnable{  
        long checkDelay = 10;  
        long keepAliveDelay = 14000;  
        public void run() {  
            while(running){  
                if(System.currentTimeMillis()-lastSendTime>keepAliveDelay){  
                    try { 
                    	byte[] data = new KeepAlive().getData(); 
                        Client.this.sendObject(data);   //发送维持连接的消息~
                    } catch (IOException e) { 
                        e.printStackTrace();  
                        Client.this.stop();  
                    }  
                    lastSendTime = System.currentTimeMillis();  
                }else{  
                    try {  
                        Thread.sleep(checkDelay);  
                    } catch (InterruptedException e) {  
                        e.printStackTrace();  
                        Client.this.stop();  
                    }  
                }  
            }  
        }  
    }  
      
    class ReceiveWatchDog implements Runnable{  
        public void run() {
        	int count = 1;
            while(running){  
                try {
                	InputStream in = socket.getInputStream();
					DataInputStream dis = new DataInputStream(in);
					byte[] bytes = new byte[in.available()];
					int size = dis.read(bytes);
					String hex = bytesToHex(bytes,0,size);
					if (count>0) {
						System.out.println("接收到"+hex);
						count--;
					}
                } catch (Exception e) {  
                    e.printStackTrace();  
                    Client.this.stop();  
                }   
            }  
        }  
    }

    //数组转成16进制字符串
 public String bytesToHex(byte[] bytes, int begin, int end) {
      StringBuilder hexBuilder = new StringBuilder(2 * (end - begin));
      for (int i = begin; i < end; i++) {
          hexBuilder.append(Character.forDigit((bytes[i] & 0xF0) >> 4, 16));
          hexBuilder.append(Character.forDigit((bytes[i] & 0x0F), 16));
          hexBuilder.append(' ');
      }
      return hexBuilder.toString().toUpperCase();
    }
      
}
