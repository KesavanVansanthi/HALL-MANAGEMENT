package com.EventDatabase;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.Booking.Book;
import com.DriverPackage.Colors;
import com.Exceptions.HallInvalidException;
import com.Exceptions.InputInvalidException;
import com.databaseConnection.ConnectionDb;

public class BusinessData {
	
	
	public static void viewDetails(String userName,String password,String category){
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		
		Connection con=ConnectionDb.Connect();
		
		try {
			Statement stat = con.createStatement();
			
			ResultSet result = stat.executeQuery("SELECT h.HALL_ID, h.HALL_NAME, h.ROOMS, h.DAYS, h.AC_NONAC, h.PRICE_PER_DAY, h.ADDRESS, b.CHAIRS, b.ROOM_TYPE, b.PROJECTOR, b.WIFI FROM HALL.HALLS h INNER JOIN HALL.BUSINESSEVENT b ON h.HALL_ID = b.HALL_ID WHERE h.EVENT= 'BUSINESS EVENT'");
			System.out.println("+---------------------------------------------------------------------------------------------------------------------------------------------------------+");
			System.out.println("|  "+Colors.YELLOW+" HALL_ID  "+Colors.RESET+" |  "+Colors.YELLOW+" HALL_NAME  "+Colors.RESET+"   | "+Colors.YELLOW+" ROOMS "+Colors.RESET+" | "+Colors.YELLOW+" DAYS "+Colors.RESET+" | "+Colors.YELLOW+" AC_NONAC "+Colors.RESET+" | "+Colors.YELLOW+" PRICE_PER_DAY "+Colors.RESET+" | "+Colors.YELLOW+" ADDRESS "+Colors.RESET+" | "+Colors.YELLOW+" CHAIRS "+Colors.RESET+" | "+Colors.YELLOW+" ROOM_TYPE "+Colors.RESET+" | "+Colors.YELLOW+" PROJECTOR "+Colors.RESET+"     | "+Colors.YELLOW+" WIFI "+Colors.RESET+"         |");
			System.out.println("+---------------------------------------------------------------------------------------------------------------------------------------------------------+");
			while(result.next()) {
			    System.out.printf("|   %-9s |   %-13s |  %-6s |  %-5s |  %-9s |  %-14s |  %-8s |  %-7s |  %-10s |  %-11s    |  %-5s     |\n",
			        result.getString(1),
			        result.getString(2),
			        result.getString(3),
			        result.getString(4),
			        result.getString(5),
			        result.getString(6),
			        result.getString(7),
			        result.getString(8),
			        result.getString(9),
			        result.getString(10),
			        result.getString(11)
			    );
			}
			System.out.println("+---------------------------------------------------------------------------------------------------------------------------------------------------------+");
			char val='y';
			int cc=3;
			do {
				System.out.println(Colors.GREEN+"\n          ### Choose the Hall ID to Book ### "+Colors.RESET);
				System.out.print(Colors.YELLOW+"\n          Enter The ID : "+Colors.RESET);
				String id=sc.readLine();
				PreparedStatement st = con.prepareStatement("SELECT HALL_ID FROM HALL.HALLS WHERE HALL_ID=?");
				st.setString(1, id);
				ResultSet res = st.executeQuery();
				int count=0;
				while(res.next()) {
					count++;
				}
				if(count>0) {
					val='n';
					PreparedStatement stat1 = con.prepareStatement("SELECT h.HALL_NAME, h.ROOMS, h.DAYS, h.PRICE_PER_DAY, h.ADDRESS, b.CHAIRS, b.ROOM_TYPE, b.PROJECTOR, b.WIFI FROM HALL.HALLS h INNER JOIN HALL.BUSINESSEVENT b ON h.HALL_ID = b.HALL_ID WHERE h.HALL_ID=?");
					stat1.setString(1, id);
					ResultSet result1 = stat1.executeQuery();
					while(result1.next()) {
						System.out.println("\n                          ************************");
						System.out.println("                          * "+Colors.GREEN+"--- HALL DETAILS ---"+Colors.RESET+" *");
						System.out.println("                          ************************");
						System.out.println(Colors.YELLOW+"\n                               HALL   : "+Colors.RESET+result1.getString(1));
						System.out.println(Colors.YELLOW+"                     NUMBER OF ROOM   : "+Colors.RESET+result1.getString(2));
						System.out.println(Colors.YELLOW+"                     AVAILABLE DAYS   : "+Colors.RESET+result1.getString(3));
						System.out.println(Colors.YELLOW+"                      PRICE-PER-DAY   : "+Colors.RESET+result1.getString(4));
						System.out.println(Colors.YELLOW+"                            ADDRESS   : "+Colors.RESET+result1.getString(5));
						System.out.println(Colors.YELLOW+"                             CHAIRS   : "+Colors.RESET+result1.getString(6));
						System.out.println(Colors.YELLOW+"                          ROOM TYPE   : "+Colors.RESET+result1.getString(7));
						System.out.println(Colors.YELLOW+"                          PROJECTOR   : "+Colors.RESET+result1.getString(8));
						System.out.println(Colors.YELLOW+"                               WIFI   : "+Colors.RESET+result1.getString(9));
						System.out.println("\n          ---------------------------------------------------------------");
					}
					do {
						System.out.print(Colors.YELLOW+"          To Book Type 1 : "+Colors.RESET);
						int choice=Integer.parseInt(sc.readLine());
						if(choice==1) {
							val='n';
							new Book(id,userName,password,category);
						}
						else {
							val='y';
							throw new InputInvalidException(Colors.RED+"          --- Invalid Booking Option ---"+Colors.RESET);
						}
					}while(val=='y');
				}else {
					val='y';
					try {
						throw new HallInvalidException(Colors.RED+"\n          --- Hall_ID Doesn't Exist ---"+Colors.RESET);
					}catch(Exception e) {
						System.out.println(e.getMessage());
					}
				}
				cc--;
			}while(val=='y' && cc>0);
			
			
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static int validDays(String hallID,int days) throws SQLException {
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		boolean valid=false;
		int day=0;

			Connection con=ConnectionDb.Connect();
			
			//step 3.Creating a Statement
			Statement stat = con.createStatement();
			
			ResultSet result = stat.executeQuery("SELECT DAYS FROM HALL.HALLS WHERE HALL_ID='"+hallID+"'");
			
			while(result.next()) {
				if(result.getInt(1)>=days){
					valid = true;
				}else {
					day=result.getInt(1);
					valid = false;
				}
			}
			
		
		if(valid) {
			return day;
		}else {
			return day;
		}
	}

}
