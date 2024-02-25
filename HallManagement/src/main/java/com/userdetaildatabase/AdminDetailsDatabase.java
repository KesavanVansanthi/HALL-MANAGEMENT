package com.userdetaildatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.DriverPackage.Colors;
import com.database.UserAdminUpdate;
import com.databaseConnection.ConnectionDb;

public class AdminDetailsDatabase {
	public void view(String username,String pass) throws SQLException {
			Connection con=ConnectionDb.Connect();
			
			//step 3.Creating a Statement
			Statement stat = con.createStatement();
			
			ResultSet result = stat.executeQuery("SELECT USER_NAME, FIRST_NAME, LAST_NAME, AGE, DATE_OF_BIRTH, CITY, PHONE_NO, EMAIL, PASSWORD FROM HALL.ADMINS WHERE USER_NAME='"+username+"' AND PASSWORD='"+pass+"'");
			
			while(result.next()) {
				System.out.println("\n             *******************************");
				System.out.println("             *     "+Colors.LAVENDER+"--- ADMIN DETAILS ---"+Colors.RESET+"   *");
				System.out.println("             *******************************");
				System.out.println(Colors.YELLOW+"            USER_NAME     : "+Colors.RESET+result.getString(1));
				System.out.println(Colors.YELLOW+"            FULL NAME     : "+Colors.RESET+result.getString(2)+" "+result.getString(3));
				System.out.println(Colors.YELLOW+"            AGE           : "+Colors.RESET+result.getString(4));
				System.out.println(Colors.YELLOW+"            DATE OF BIRTH : "+Colors.RESET+result.getDate(5));
				System.out.println(Colors.YELLOW+"            CITY          : "+Colors.RESET+result.getString(6));
				System.out.println(Colors.YELLOW+"            PHONE NUMBER  : "+Colors.RESET+result.getString(7));
				System.out.println(Colors.YELLOW+"            MAIL          : "+Colors.RESET+result.getString(8));
				String dotPass="";
				for(int i=0;i<result.getString(9).length();i++) {
					dotPass+="*";
				}
				System.out.println(Colors.YELLOW+"            PASSWORD      : "+Colors.RESET+dotPass);
			}

		
	}
	
	
	public void dataUpdate(String username,String pass,int field,String value) {
		new UserAdminUpdate(username,pass,field,value);
	}
}

