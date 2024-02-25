package com.EventDatabase;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import com.DriverPackage.Colors;
import com.Exceptions.InvalidBookingException;
import com.databaseConnection.ConnectionDb;

public class AdminBookData {
	public AdminBookData(){
		
	}
	public static void pendingBooking() {
		Connection con=ConnectionDb.Connect();
		try{
			Statement stat=con.createStatement();
			
			ResultSet result = stat.executeQuery("SELECT * FROM HALL.BOOKING_DETAILS WHERE STATUS='PENDING'");
			System.out.println("\n          +--------------------------------------------------------------------------------------------------------------------------------------------+");
			System.out.println("          |  "+Colors.YELLOW+"  BOOKING_ID  "+Colors.RESET+"  |  "+Colors.YELLOW+"  RENTING_DAYS  "+Colors.RESET+"  |  "+Colors.YELLOW+"  START_DATE  "+Colors.RESET+"  |  "+Colors.YELLOW+"  END_DATE  "+Colors.RESET+"  |  "+Colors.YELLOW+"  HALL_ID  "+Colors.RESET+"  |  "+Colors.YELLOW+"  STATUS  "+Colors.RESET+"  |  "+Colors.YELLOW+"  USER_ID  "+Colors.RESET+"  |  "+Colors.YELLOW+"  ADMIN_ID   "+Colors.RESET+"  |");
			System.out.println("          +--------------------------------------------------------------------------------------------------------------------------------------------+");
			while(result.next()) {
			    System.out.printf("          |  %-12d    |  %-14d    |  %-11s     |  %-8s    |  %-8s     |  %-8s    |  %-8s     |  %-8s       |\n",
			        result.getInt(1),
			        result.getInt(2),
			        result.getDate(3),
			        result.getDate(4),
			        result.getString(5),
			        result.getString(6),
			        result.getInt(7),
			        result.getInt(8)
			    );
			}
			System.out.println("          +--------------------------------------------------------------------------------------------------------------------------------------------+");

		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	


	
	public static Boolean verifyBooking(int BookingID) throws InvalidBookingException {
		
		Connection con = ConnectionDb.Connect();
		
		boolean valid=false;
		
		final String Query ="SELECT HALL_ID FROM HALL.HALLS WHERE HALL_ID IN (SELECT HALL_ID FROM HALL.BOOKING_DETAILS WHERE STATUS='APPROVED')";
		
		
		
		final String Query_approved = "SELECT START_DATE,END_DATE,HALL_ID FROM HALL.BOOKING_DETAILS WHERE HALL_ID=? AND STATUS='APPROVED'";
		
		final String Query_pending = "SELECT START_DATE,END_DATE,RENTING_DAYS FROM HALL.BOOKING_DETAILS WHERE BOOKING_ID=? AND STATUS='PENDING'";
		
		final String Query_hall ="SELECT DAYS,HALL_ID FROM HALL.HALLS WHERE HALL_ID=(SELECT HALL_ID FROM HALL.BOOKING_DETAILS WHERE BOOKING_ID=?)"; 
		
		final String select_book="SELECT HALL_ID FROM HALL.BOOKING_DETAILS";
		
		try {
			
			PreparedStatement statemt=con.prepareStatement(Query);
			
			ResultSet rs=statemt.executeQuery();
			
			
			while(rs.next()) {
				PreparedStatement stat = con.prepareStatement(Query_approved);
				stat.setString(1, rs.getString(1));
				PreparedStatement stat1 = con.prepareStatement(Query_pending);
				stat1.setInt(1, BookingID);
				PreparedStatement stat2 = con.prepareStatement(Query_hall);
				stat2.setInt(1, BookingID);
				PreparedStatement stat3 = con.prepareStatement(select_book);
				
				
				ResultSet result = stat.executeQuery();
				ResultSet result1 = stat1.executeQuery();
				ResultSet result2 = stat2.executeQuery();
				ResultSet result3 = stat3.executeQuery();

				
				while(result.next()) {
					if(result3.next() && result2.next() && result1.next()) {
						if(result3.getString(1).equals(result.getString(3))) {
							if(!(result1.getDate(1).equals(result.getDate(1)) && result1.getDate(1).equals(result.getDate(2)))){
								if(!(result1.getDate(1).toLocalDate().isBefore(result.getDate(2).toLocalDate()) && result1.getDate(1).toLocalDate().isAfter(result.getDate(1).toLocalDate()))){
									if(!(result1.getInt(3)<=result2.getInt(1))) {
										throw new InvalidBookingException(Colors.RED+"\n           --- Sorry the Hall only Available for "+Colors.RESET+result2.getInt(1)+Colors.RED+"---"+Colors.RESET);
									}else {
										System.out.println(Colors.GREEN+"\n          There is #### No Conflicts #### "+Colors.RESET+" \n          "+Colors.RED+" ---"+Colors.RESET+" You can Give Access "+Colors.RED+"---"+Colors.RESET);
										valid = true;
									}
									
								}else {
									throw new InvalidBookingException(Colors.RED+"\n          ERROR : --- Their Starting date is in between in a already Approved One --- "+Colors.RESET);
								}
								
								
							}
							else {
								valid=false;
								throw new InvalidBookingException(Colors.RED+"\n           ERROR : --- Matched with The Hall that's Already been Booked ---"+Colors.RESET);
							}
						}else {
							System.out.println(Colors.GREEN+"\n          There is No Hall booked yet for the given Hall_id  "+Colors.RESET+Colors.RED+"\n           ---"+Colors.RESET+" You can Give Access "+Colors.RED+"---"+Colors.RESET);
							valid=true;
							
						}
					}
				}
			}
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
		if(valid) {
			return true;
		}else {
			return false;
		}
		
		
	}
	
	
	public static void finalstatus(String status,int BookingID) {
		Connection con = ConnectionDb.Connect();
		try {
			System.out.println("\n                  "+Colors.CYAN+status.toUpperCase()+Colors.RESET);
			PreparedStatement stat = con.prepareStatement("UPDATE HALL.BOOKING_DETAILS SET STATUS=? WHERE BOOKING_ID=?");
			stat.setString(1, status.toUpperCase());
			stat.setInt(2, BookingID);
			
			stat.execute();
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
	}

}