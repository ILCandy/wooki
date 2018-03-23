package com.wooki.system.air.server;

import com.wooki.system.air.gateway.entity.AirDevice;
import com.wooki.system.air.utils.DruidUtil;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.*;
import java.util.concurrent.ConcurrentHashMap;

public class SocketManager {

	private int port;
	
	public SocketManager() {};
	private static final SocketManager sm = new SocketManager();
	public static SocketManager getSocketManager() {
		return sm;
	}
	
	private ConcurrentHashMap<Integer, ClientSocket> sockeCstMap = new ConcurrentHashMap<Integer, ClientSocket>();
	private ConcurrentHashMap<Integer, Socket> socketMap = new ConcurrentHashMap<Integer, Socket>();

	public void add(Socket socket, int skey) {
		socketMap.put(skey, socket);
	}
	//向网关发送数据
	public void sendData(int port,byte[] data) {
		Socket sendSocket;
		DataOutputStream dos;
		sendSocket = socketMap.get(port);
		try {
			dos = new DataOutputStream(sendSocket.getOutputStream());
			dos.write(data,0,data.length);
			System.out.println("向网关："+sendSocket.getPort()+"发送数据:"+bytesToHex(data,0,data.length));
			dos.flush();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("发送失败~~~");
		}
	}

	public void add(ClientSocket cs ,int port) {
		this.port = port;
		sockeCstMap.put(port, cs);
	}
	

	//向客户端发送消息
	public void publish(ClientSocket cs,byte[] data, int portKey) {
		ClientSocket csClientSocket = sockeCstMap.get(portKey);
		Socket socket = csClientSocket.getSocket();
		if (csClientSocket!=null) {
			csClientSocket.send(data,socket);
		}
	}
	
	public void saveGateway(String hex) throws SQLException {
		String str = hex.replace(" ", "");
		String uid = str.substring(4,36);
		Connection connection = DruidUtil.getConnection();
		String sql = "select * from air_gateway where uid = ?";
		PreparedStatement prepareStatement = connection.prepareStatement(sql);
		prepareStatement.setString(1, uid);
		ResultSet resultSet = prepareStatement.executeQuery();
		if (resultSet.next()) {
			System.out.println("数据库有了,更新端口~");
			sql = "update air_gateway set port = ? where uid = ?";
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setInt(1,port);
			prepareStatement.setString(2,uid);
			prepareStatement.executeUpdate();
		}else {
			System.out.println("port="+port);
			sql = "insert into air_gateway values(?,?,?,?,?,?,?,?,?)";
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setNull(1, Types.INTEGER);
			prepareStatement.setDate(2,new Date(System.currentTimeMillis()));
			prepareStatement.setString(3, "未绑定公司");
			prepareStatement.setInt(4,0);
			prepareStatement.setString(5, "空调网关");
			prepareStatement.setInt(6,port);
			prepareStatement.setInt(7, 1);
			prepareStatement.setString(8, uid);
			prepareStatement.setNull(9,Types.DATE);
			prepareStatement.executeUpdate();
			System.out.println("保存成功");
		}
		DruidUtil.release(resultSet, prepareStatement, connection);
	}

	//保存网关下的设备信息
	public void saveDevice(byte[] bytes, int port) throws SQLException {
		PreparedStatement prepareStatement = null;
		Connection connection = DruidUtil.getConnection();
		String sql = "select * from air_gateway where port = ?";
		prepareStatement = connection.prepareStatement(sql);
		prepareStatement.setInt(1,port);
		ResultSet resultSet = prepareStatement.executeQuery();
		while (resultSet.next()){
			String gatewayUid = resultSet.getString("uid");
			int companyId = resultSet.getInt("companyid");
			int j =3;
			String checksum = Integer.toHexString(bytes[bytes.length-1]);
			for (int i = 0; i < bytes[3] ; i++) {
				sql = "insert into air_device values(?,?,?,?,?,?,?,?,?,?,?,?)";
				prepareStatement = connection.prepareStatement(sql);
				prepareStatement.setNull(1, Types.INTEGER);
				prepareStatement.setDate(2,new Date(System.currentTimeMillis()));
				prepareStatement.setString(3,checksum);
				prepareStatement.setInt(4,companyId);
				prepareStatement.setString(5,"空调设备");
				prepareStatement.setString(6,gatewayUid);
				prepareStatement.setInt(7,0);
				prepareStatement.setString(8,Integer.toHexString(bytes[j+2]));
				prepareStatement.setInt(9,1);
				prepareStatement.setString(10,Integer.toHexString(bytes[j+1]));
				prepareStatement.setString(11,Integer.toHexString(bytes[j+3]));
				prepareStatement.setNull(12,Types.DATE);
				j = j+10;
				prepareStatement.executeUpdate();
			}
		}
	}

	//数组转成16进制字符串
	public String bytesToHex(byte[] bytes, int begin, int end) {
		StringBuilder hexBuilder = new StringBuilder(2 * (end - begin));
		for (int i = begin; i < end; i++) {
			hexBuilder.append(Character.forDigit((bytes[i] & 0xF0) >> 4, 16)); // 转化高四位
			hexBuilder.append(Character.forDigit((bytes[i] & 0x0F), 16)); // 转化低四位
			hexBuilder.append(' '); // 加一个空格将每个字节分隔开
		}
		return hexBuilder.toString().toUpperCase();
	}

}
