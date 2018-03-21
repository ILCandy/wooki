package com.wooki.system.air.server;

import org.springframework.stereotype.Service;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

@Service
public class ClientSocket implements Runnable {
	
	private Socket socket;
	private boolean clientFlag = true;
	private long receiveTimeDelay=200000;
	long lastReceiveTime = System.currentTimeMillis();
	
	public ClientSocket() {
	}
	
	public ClientSocket(Socket s) {
		this.socket = s;
	}
	
	@Override
	public void run() {
		byte[] bytes = new byte[1024];
		int k = 0;
		int openThread = 1;
		while (clientFlag) {
			long currentTimeMillis = System.currentTimeMillis();
			if (currentTimeMillis-lastReceiveTime>receiveTimeDelay) {
				System.out.println("超时断开连接");
				overThis();
			}else {
				try {
					InputStream in = socket.getInputStream();
					DataInputStream dis = new DataInputStream(in);
					int size = dis.read(bytes);
					String hex = bytesToHex(bytes,0,size);
					lastReceiveTime = System.currentTimeMillis();//刷新时间
					System.out.println("服务端接收到的"+k+++"数据："+hex);
					byte[] gatewayByte = {(byte) 0xAA,0x55};
//					System.out.println(gatewayByte[0]+":"+gatewayByte[1]);
					//如果是新的网关接入，保存序列号UID到数据库
					if (gatewayByte[0]==bytes[0] && gatewayByte[1]==bytes[1]) {
						System.out.println("保存序列号");
						SocketManager.getSocketManager().saveGateway(hex);
					} else if (bytes[0]==9) {
						System.out.println("进来了~~~");
						String str = "";
						byte[] data = null;
						for (int i = 1; i < size; i++) {
							if (bytes[i]!=9) {
								str += bytes[i];
							}else {
								System.out.println("i="+i);
								data = new byte[size-str.length()-2];
								System.out.println("发送data的长度"+data.length);
								for (int j = 0; j < data.length; j++) {
									i++;
									data[j] = bytes[i];
								}
							}
						}
						
						System.out.println("命令data:"+bytesToHex(data,0,data.length));
						int portKey = Integer.valueOf(str).intValue();
						if (openThread>0) {
							openThread--;
							SocketManager.getSocketManager().publish(this, data,portKey);
						}
						
					}
					
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("异常连接断开");
					overThis();
				}
			}
		}
	}

	private void overThis() {
		if(clientFlag)clientFlag=false;  
        if(socket!=null){  
            try { 
//            	socketMap.remove((socket.getPort()+""));
                socket.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        System.out.println("关闭"+socket.getRemoteSocketAddress());
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
	 
	private Socket sendSocket;
	public void send(byte[] data,Socket socket) {
		sendSocket = socket;
		DataOutputStream dos;
		try {
			dos = new DataOutputStream(sendSocket.getOutputStream());
			dos.write(data,0,data.length);
			System.out.println("向网关："+sendSocket.getPort()+"发送数据:"+bytesToHex(data,0,data.length));
			dos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Socket getSocket() {
		return socket;
	}

}
