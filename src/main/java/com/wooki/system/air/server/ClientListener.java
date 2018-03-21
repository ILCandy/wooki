package com.wooki.system.air.server;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@Service
public class ClientListener implements Runnable {
	
	private int port;
	private ServerSocket serverSocket;
	private boolean flag = true;
	
	public ClientListener() {
	}
	public ClientListener(int port) {
		this.port = port;
	}

	@Override
	public void run() {
		try {
			serverSocket = new ServerSocket(port);
			while (flag) {
				Socket socket = serverSocket.accept();
				System.out.println("端口"+socket.getPort()+"连上了~~~~");
				ClientSocket cs = new ClientSocket(socket);
				int skey = socket.getPort();
				new Thread(cs).start();
				SocketManager.getSocketManager().add(cs,skey);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("端口已经被占用，请重新设置端口");
			ClientListener.this.stopMe();
		}
	}

	private void stopMe() {
		if(flag)flag=false;  
	}

}
