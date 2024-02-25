package com.databaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDb {
	public static Connection Connect() {
		final String URL="jdbc:oracle:thin:@localhost:1521:xe";
		final String userName="SYSTEM";
		final String password="130602";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			//2.create connection
			Connection con=DriverManager.getConnection(URL,userName,password);
			
			return con;
			
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
