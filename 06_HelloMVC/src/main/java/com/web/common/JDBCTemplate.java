package com.web.common;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCTemplate {
	
	public static Connection getConnection() {
		Connection conn=null;
		String path=JDBCTemplate.class
		.getResource("/driver.properties").getPath();
		try (FileReader fr=new FileReader(path);){
			Properties driver=new Properties();
			driver.load(fr);
			Class.forName(driver.getProperty("driver"));
			conn=DriverManager.getConnection(
					driver.getProperty("url")
					,driver.getProperty("user")
					,driver.getProperty("pw"));
			conn.setAutoCommit(false);			
		}catch(ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
//	public static void close(Object o) {
//		try {
//			if(o instanceof Connection ) {
//				Connection val=(Connection)o;
//				if(val!=null && !val.isClosed()) {
//						val.close();
//				}
//			}
//		}catch(SQLException e) {
//			e.printStackTrace();
//		}
//	}
	public static void close(Connection val) {
		try {
			if(val!=null && !val.isClosed()) val.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public static void close(Statement val) {
		try {
			if(val!=null && !val.isClosed()) val.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public static void close(ResultSet val) {
		try {
			if(val!=null && !val.isClosed()) val.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public static void commit(Connection val) {
		try {
			if(val!=null && !val.isClosed()) val.commit();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public static void rollback(Connection val) {
		try {
			if(val!=null && !val.isClosed()) val.rollback();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
