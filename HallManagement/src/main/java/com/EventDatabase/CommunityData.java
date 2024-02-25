package com.EventDatabase;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.DriverPackage.*;
import com.Booking.Book;
import com.Exceptions.InputInvalidException;
import com.databaseConnection.ConnectionDb;

public class CommunityData {
	public static void viewDetails(String urName,String pass,String category) throws InputInvalidException{
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		
			Connection con=ConnectionDb.Connect();
			
			try {
				Statement stat = con.createStatement();
				
				ResultSet result = stat.executeQuery("SELECT HALL_ID,HALL_NAME,ROOMS,DAYS,AC_NONAC,PRICE_PER_DAY,ADDRESS FROM HALL.HALLS WHERE EVENT ='COMMUNITY EVENT'");
				System.out.println("\n         +-------------------------------------------------------------------------------------------------------------------------+");
				System.out.println("         |   "+Colors.YELLOW+"HALL_ID"+Colors.RESET+"      |   "+Colors.YELLOW+"HALL_NAME"+Colors.RESET+"       |  "+Colors.YELLOW+"ROOMS"+Colors.RESET+"     |  "+Colors.YELLOW+"DAYS"+Colors.RESET+"   |  "+Colors.YELLOW+"AC_NONAC"+Colors.RESET+"    |  "+Colors.YELLOW+"PRICE_PER_DAY"+Colors.RESET+"  |  "+Colors.YELLOW+"ADDRESS"+Colors.RESET+"                   |");
				System.out.println("         +-------------------------------------------------------------------------------------------------------------------------+");
				while (result.next()) {
				    System.out.printf("         |   %-7s      |   %-14s  |   %-7s  |   %-3s   |   %-7s    |    %-10s   |  %-15s        |\n",
				        result.getString(1),
				        result.getString(2),
				        result.getString(3),
				        result.getString(4),
				        result.getString(5),
				        result.getString(6),
				        result.getString(7)
				    );
				}
				System.out.println("         +-------------------------------------------------------------------------------------------------------------------------+");

				System.out.println(Colors.GREEN+"\n          ### Choose the Hall ID to Book ### "+Colors.RESET);
				System.out.print(Colors.YELLOW+"\n          Enter The ID : "+Colors.RESET);
				String id=sc.readLine();
				int count=0;
				ResultSet result1 = stat.executeQuery("SELECT HALL_NAME,ROOMS,DAYS,AC_NONAC,PRICE_PER_DAY,ADDRESS FROM HALL.HALLS WHERE HALL_ID='"+id+"'");
				 do{
					 if(result1.next()) {
						 count++;
					 }
					if(count>0) {
						System.out.println("\n                ************************");
						System.out.println("                * "+Colors.GREEN+"--- HALL DETAILS ---"+Colors.RESET+" *");
						System.out.println("                ************************");
						System.out.println(Colors.YELLOW+"\n          HALL             : "+Colors.RESET+result1.getString(1));
						System.out.println(Colors.YELLOW+"          NUMBER OF ROOM   : "+Colors.RESET+result1.getString(2));
						System.out.println(Colors.YELLOW+"          AVAILABLE DAYS   : "+Colors.RESET+result1.getString(3));
						System.out.println(Colors.YELLOW+"          PRICE-PER-DAY    : "+Colors.RESET+result1.getString(4));
						System.out.println(Colors.YELLOW+"          ADDRESS          : "+Colors.RESET+result1.getString(5));
						System.out.println("\n          ---------------------------------------------------------------");
					}else {
						throw new InputInvalidException(Colors.RED+"          --- Hall ID Not Exist ---`"+Colors.RESET);
					}
					
				}while(result1.next());
				char val='y';
				do {
					System.out.print(Colors.YELLOW+"          To Book Type 1 : "+Colors.RESET);
					int choice=Integer.parseInt(sc.readLine());
					if(choice==1) {
						val='n';
						new Book(id,urName,pass,category);
					}
					else {
						val='y';
						throw new InputInvalidException(Colors.RED+"          --- Invalid Booking Option ---"+Colors.RESET);
					}
				}while(val=='y');
				
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
