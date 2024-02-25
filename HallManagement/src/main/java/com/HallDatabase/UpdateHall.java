package com.HallDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import com.databaseConnection.ConnectionDb;

public class UpdateHall {

	public static void updateName(String hallName,String hallID) throws SQLException {
		
		final String Update_Value="UPDATE HALL.HALLS SET HALL_NAME='?' WHERE HALL_ID ='?'";
		
		Connection con=ConnectionDb.Connect();
		
		//step 3.Creating a Statement
		PreparedStatement stat = con.prepareStatement(Update_Value);
		stat.setString(1,hallName );
		stat.setString(2,hallID);
		
		stat.execute();
	}
	
	public static void updateRoom(int room,String hallID) throws SQLException {
		
		final String Update_Value="UPDATE HALL.HALLS SET ROOMS=? WHERE HALL_ID ='?'";
		
		Connection con=ConnectionDb.Connect();
		
		//step 3.Creating a Statement
		PreparedStatement stat = con.prepareStatement(Update_Value);
		stat.setInt(1, room);
		stat.setString(2,hallID);
		
		stat.execute();
	}
	
	public static void updateDays(int days,String hallID) throws SQLException {
		
		final String Update_Value="UPDATE HALL.HALLS SET DAYS=? WHERE HALL_ID ='?'";

		Connection con=ConnectionDb.Connect();
		
		//step 3.Creating a Statement
		PreparedStatement stat = con.prepareStatement(Update_Value);
		stat.setInt(1, days);
		stat.setString(2,hallID);
		
		stat.execute();
		
	}
	
	public static void updateAc(char ac,String hallID) throws SQLException {
		
		Connection con=ConnectionDb.Connect();
		
		//step 3.Creating a Statement
		
		if(ac=='y') {
			PreparedStatement stat = con.prepareStatement("UPDATE HALL.HALLS SET AC_NONAC='AC' WHERE HALL_ID ='?'");
			
			stat.setString(1,hallID);
			
			stat.execute();
		}
		else {
			PreparedStatement stat = con.prepareStatement("UPDATE HALL.HALLS SET AC_NONAC='Non-AC' WHERE HALL_ID ='?'");
			
			stat.setString(1,hallID);
			
			stat.execute();
		}
			
			
		
	}
	
	public static void updatePrice(int price,String hallID) throws SQLException {
		
		final String Update_Value="UPDATE HALL.HALLS SET PRICE_PER_DAY=? WHERE HALL_ID ='?'";

		Connection con=ConnectionDb.Connect();
		
		//step 3.Creating a Statement
		PreparedStatement stat = con.prepareStatement(Update_Value);
		stat.setInt(1, price);
		stat.setString(2,hallID);
		
		stat.execute();
		
	}
	
	public static void updateInterest(int interest,String hallID) throws SQLException {
		
		
		final String Update_Value="UPDATE HALL.HALLS SET INTEREST=? WHERE HALL_ID ='?'";

		Connection con=ConnectionDb.Connect();
		
		//step 3.Creating a Statement
		PreparedStatement stat = con.prepareStatement(Update_Value);
		stat.setInt(1, interest);
		stat.setString(2,hallID);
			
		stat.execute();
		
	}
	
	public static void updateEvent(String event,String hallID) throws SQLException {
		
		final String Update_Value="UPDATE HALL.HALLS SET EVENT='?' WHERE HALL_ID ='?'";

		Connection con=ConnectionDb.Connect();
		
		//step 3.Creating a Statement
		PreparedStatement stat = con.prepareStatement(Update_Value);
		stat.setString(1, event);
		stat.setString(2,hallID);
		
		
		stat.execute();
			
		
	}
}
