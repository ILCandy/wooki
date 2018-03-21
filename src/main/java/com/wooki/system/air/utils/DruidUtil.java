package com.wooki.system.air.utils;

import com.alibaba.druid.pool.DruidDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DruidUtil {
	
	static DruidDataSource ds;
	static {
		ds = new DruidDataSource();
		ds.setUsername("root");
		ds.setPassword("123");
		ds.setUrl("jdbc:mysql://localhost:3306/wookitech");
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setInitialSize(5);
	}
	
	public static Connection getConnection() throws SQLException{
		Connection connection = ds.getConnection();
		return connection;
	}
	
	public static void release(ResultSet resultSet,Statement statement,Connection connection) throws SQLException{
		if (resultSet != null) {
			resultSet.close();
		}
		if (statement != null) {
			statement.close();
		}
		if (connection != null) {
			connection.close();
		}
	}
	
}
