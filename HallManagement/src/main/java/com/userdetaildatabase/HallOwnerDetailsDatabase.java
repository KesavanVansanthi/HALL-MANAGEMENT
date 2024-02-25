package com.userdetaildatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.DriverPackage.Colors;
import com.database.UserCustomerUpdate;
import com.databaseConnection.ConnectionDb;

public class HallOwnerDetailsDatabase {
	public void view(String username,String pass) throws SQLException {
		
			Connection con=ConnectionDb.Connect();
			
			//step 3.Creating a Statement
			Statement stat = con.createStatement();
			
			ResultSet result = stat.executeQuery("SELECT USER_NAME, FIRST_NAME, LAST_NAME, AGE, DATE_OF_BIRTH, CITY, PHONE_NO, EMAIL, PASSWORD, CATEGORY FROM HALL.USERS WHERE USER_NAME='"+username+"' AND PASSWORD='"+pass+"' AND CATEGORY='HALLOWNER'");
			
			while(result.next()) {
				System.out.println("\n             *******************************");
				System.out.println("             *  "+Colors.LAVENDER+"--- HALL OWNER DETAILS ---"+Colors.RESET+" *");
				System.out.println("             *******************************");
				System.out.println(Colors.YELLOW+"\n            USER_NAME     : "+Colors.RESET+result.getString(1));
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
				System.out.println(Colors.YELLOW+"            CATEGORY      : "+Colors.RESET+result.getString(10));
			}

		
	}
	
	public static void contactHallOwnerDetails(int bookingID) {
		Connection con = ConnectionDb.Connect();
		try {
			PreparedStatement stat = con.prepareStatement("SELECT FIRST_NAME,LAST_NAME,PHONE_NO,EMAIL FROM HALL.USERS WHERE USER_ID=(SELECT USER_ID FROM HALL.BOOKING_DETAILS WHERE BOOKING_ID=?)");
			stat.setInt(1, bookingID);
			ResultSet result=stat.executeQuery();
			while(result.next()) {
				System.out.println("\n              ------------------------");
				System.out.println("              -: "+Colors.GREEN+"HALL OWNER DETAILS"+Colors.RESET+" :- ");
				System.out.println("              ------------------------");
				System.out.println(Colors.YELLOW+"\n            FULL NAME     : "+Colors.RESET+result.getString(2)+" "+result.getString(3));
				System.out.println(Colors.YELLOW+"            PHONE NUMBER  : "+Colors.RESET+result.getString(7));
				System.out.println(Colors.YELLOW+"            MAIL          : "+Colors.RESET+result.getString(8));
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	public void dataUpdate(String username,String pass,int field,String value) throws SQLException {
		new UserCustomerUpdate(username,pass,field,value);
	}
}

