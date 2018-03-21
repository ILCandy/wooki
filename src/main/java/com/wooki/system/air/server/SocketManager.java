package com.wooki.system.air.server;

import com.wooki.system.air.utils.DruidUtil;

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
	
	private ConcurrentHashMap<Integer, ClientSocket> socketMap = new ConcurrentHashMap<Integer, ClientSocket>();
	public void add(ClientSocket cs ,int port) {
		this.port = port;
		socketMap.put(port, cs);
	}
	

	//向客户端发送消息
	public void publish(ClientSocket cs,byte[] data, int portKey) {
		ClientSocket csClientSocket = socketMap.get(portKey);
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
	
}
