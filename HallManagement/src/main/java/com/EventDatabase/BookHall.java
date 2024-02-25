package com.EventDatabase;

import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.sql.Date;
import com.Booking.Book;
import com.DriverPackage.Colors;
import com.databaseConnection.ConnectionDb;
import com.users.Users;

public class BookHall {
	private int days;
	private LocalDate startDate;
	private LocalDate endDate;
	private String hallID;
	private String userName;
	private String password;
	private String event;
	private String Query_user_id;
	private String Query_insert;
	private Book book;
	
	private Users user;
	
	public BookHall(Users user) {
		this.setUser(user);
	}
	
	public BookHall(int days,LocalDate startDate,LocalDate endDate,String hallID,String userName,String password,String event) {
		this.setDays(days);
		this.setStartDate(startDate);
		this.setEndDate(endDate);
		this.setHallID(hallID);
		this.setUserName(userName);
		this.setPassword(password);
		this.setEvent(event);
	}
	
	public void insertdetails(String Category) {

		Connection con=ConnectionDb.Connect();
		
		
		if(Category.equalsIgnoreCase("Admin")) {
			Query_user_id="SELECT ADMIN_ID FROM HALL.ADMINS WHERE USER_NAME=? AND PASSWORD=?";
			Query_insert="INSERT INTO HALL.BOOKING_DETAILS (RENTING_DAYS, START_DATE, END_DATE, HALL_ID,STATUS,ADMIN_ID) VALUES (?,?,?,?,?,?)";

		}else {
			Query_user_id="SELECT USER_ID FROM HALL.USERS WHERE USER_NAME=? AND PASSWORD=?";
			Query_insert="INSERT INTO HALL.BOOKING_DETAILS (RENTING_DAYS, START_DATE, END_DATE, HALL_ID,STATUS,USER_ID) VALUES (?,?,?,?,?,?)";

		}
		
		try {
			
			PreparedStatement stat1 = con.prepareStatement(Query_user_id);
			stat1.setString(1, this.getUserName());
			stat1.setString(2, this.getPassword());
			int user_id =0;
			ResultSet result = stat1.executeQuery();
			while(result.next()) {
				user_id=result.getInt(1);
			}
			PreparedStatement stat = con.prepareStatement(Query_insert);
			stat.setInt(1, this.getDays());
			stat.setDate(2, Date.valueOf(this.getStartDate()));
			stat.setDate(3, Date.valueOf(this.getEndDate()));
			stat.setString(4, this.getHallID());
			stat.setString(5, "PENDING");
			stat.setInt(6, user_id);
			
			
			stat.execute();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	public static List<String> fetchBooking(String userName,String password,String category) {
		Connection con = ConnectionDb.Connect();
		List<String> message = new ArrayList<String>();
		try {
			if(category.equalsIgnoreCase("Admin")) {
				PreparedStatement stat = con.prepareStatement("SELECT BOOKING_ID,STATUS FROM HALL.BOOKING_DETAILS WHERE ADMIN_ID=(SELECT ADMIN_ID FROM HALL.ADMINS WHERE USER_NAME=? AND PASSWORD=?)");
				stat.setString(1, userName);
				stat.setString(2, password);
				
				ResultSet result = stat.executeQuery();
				while(result.next()) {
					message.add(result.getString(2)+" "+result.getInt(1));
				}
			}
			else {
				PreparedStatement stat = con.prepareStatement("SELECT BOOKING_ID,STATUS FROM HALL.BOOKING_DETAILS WHERE USER_ID=(SELECT USER_ID FROM HALL.USERS WHERE USER_NAME=? AND PASSWORD=?)");
				stat.setString(1, userName);
				stat.setString(2, password);
				
				ResultSet result = stat.executeQuery();
				while(result.next()) {
					message.add(result.getString(2)+" "+result.getInt(1));
				}
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return message;
		
	}
	
	public static int paymentDetails(int bookingID) {
		Connection con = ConnectionDb.Connect();
		int day=0;
		int price=0;
		try {
			
			PreparedStatement stat = con.prepareStatement("SELECT b.BOOKING_ID,h.HALL_NAME,h.DAYS,h.PRICE_PER_DAY FROM HALL.BOOKING_DETAILS b JOIN HALL.HALLS h ON b.HALL_ID = h.HALL_ID where b.Booking_id=?");
			stat.setInt(1, bookingID);
			ResultSet result = stat.executeQuery();
			while(result.next()) {
				System.out.println("\n          -----------------------------------------");
				System.out.println(Colors.LAVENDER+"                   ### PAYMENT DETAILS ###"+Colors.RESET);
				System.out.println("          -----------------------------------------");
				System.out.println(Colors.YELLOW+"                 BOOKING ID : "+Colors.RESET+result.getInt(1));
				System.out.println(Colors.YELLOW+"                  HALL NAME : "+Colors.RESET+result.getString(2));
				System.out.println(Colors.YELLOW+"               RENTING DAYS : "+Colors.RESET+result.getInt(3));
				System.out.println(Colors.YELLOW+"              PRICE-PER-DAY : "+Colors.RESET+result.getInt(4));
				
				day=result.getInt(3);
				price=result.getInt(4);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return day*price;
		
	}
	
	public static void deleteBook(int bookingID) {
		Connection con = ConnectionDb.Connect();
		try {
			PreparedStatement stat = con.prepareStatement("DELETE FROM HALL.BOOKING_DETAILS WHERE BOOKING_ID=?");
			stat.setInt(1, bookingID);
			
			stat.execute();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void cancelBooking(int bookingID) {
		Connection con = ConnectionDb.Connect();
		try {
			PreparedStatement stat = con.prepareStatement("UPDATE HALL.BOOKING_DETAILS SET STATUS='CANCELLED' WHERE BOOKING_ID=?");
			stat.setInt(1,bookingID);
			stat.execute();
			reAssign(bookingID,"Cancelled");
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void refund(String userName,String password,String Category){
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		Connection con = ConnectionDb.Connect();
		try {
			if(Category.equalsIgnoreCase("Admin")){
				PreparedStatement stat = con.prepareStatement("SELECT b.BOOKING_ID,b.RENTING_DAYS,b.HALL_ID,b.STATUS,h.HALL_NAME,h.PRICE_PER_DAY FROM HALL.BOOKING_DETAILS b JOIN HALL.HALLS h ON b.HALL_ID=h.HALL_ID WHERE b.ADMIN_ID IN (SELECT ADMIN_ID FROM HALL.ADMINS WHERE USER_NAME=? AND PASSWORD=?) AND b.STATUS != 'CANCELLED'");
				stat.setString(1, userName);
				stat.setString(2, password);
				
				ResultSet result = stat.executeQuery();
				
				System.out.println("          +--------------------------------------------------------------------------------------+");
				System.out.println("          |  "+Colors.YELLOW+"BOOKING_ID "+Colors.RESET+" |  "+Colors.YELLOW+"RENTING_DAYS"+Colors.RESET+"  |  "+Colors.YELLOW+"HALL_ID"+Colors.RESET+"  |  "+Colors.YELLOW+"STATUS"+Colors.RESET+"  |  "+Colors.YELLOW+"HALL_NAME"+Colors.RESET+"  |  "+Colors.YELLOW+"PRICE_PER_DAY"+Colors.RESET+"  | ");
				System.out.println("          +--------------------------------------------------------------------------------------+");
				while (result.next()) {
				    int bookingId = result.getInt(1);
				    int rentingDays = result.getInt(2);
				    String hallId = result.getString(3);
				    String status = result.getString(4);
				    String hallName = result.getString(5);
				    String pricePerDay = result.getString(6);

				    System.out.println("          |  " + String.format("%-10s", bookingId) +
				                       "  |  " + String.format("%-10s", rentingDays) +
				                       "  |  " + String.format("%-8s", hallId) +
				                       "  |  " + String.format("%-8s", status) +
				                       "  |  " + String.format("%-10s", hallName) +
				                       "  |  " + String.format("%-10s", pricePerDay) +
				                       "  |");
				}
				System.out.println("          +--------------------------------------------------------------------------------------+");
				
				System.out.print("\n          Enter the Bookind ID :");
				int cnt=3;
				char val='y';
				do {
					try {cnt--;
						int bookingID=Integer.parseInt(sc.readLine());
						System.out.println(Colors.GREEN+"          Choose From the Option :"+Colors.RESET);
						System.out.println("               1.Cancel Booking");
						System.out.println("               2.Refund Amount");
						System.out.print(Colors.YELLOW+"\n          Enter You Choice : "+Colors.RESET);
						int choice=Integer.parseInt(sc.readLine());
						switch(choice) {
						case 1:
							cancelBooking(bookingID);
							System.out.println(Colors.GREEN+"          --- Your Request Accepeted CANCELLED"+Colors.RESET);
							val='n';
							break;
						case 2:
							System.out.println(Colors.GREEN+"          --- VALIDATING YOUR REQUEST --- "+Colors.RESET);
							PreparedStatement stat1=con.prepareStatement("SELECT STATUS FROM HALL.BOOKING_DETAILS WHERE BOOKING_ID=?");
							stat1.setInt(1, bookingID);
							
							ResultSet result1=stat1.executeQuery();
							int count=0;
							String status="";
							while(result1.next()) {
								count++;
								status=result1.getString(1);
							}
							if(status.equalsIgnoreCase("Booked")) {
								PreparedStatement stat3 = con.prepareStatement("SELECT TOTAL_AMOUNT,PAID_AMOUNT FROM HALL.PAYMENTS WHERE BOOKING_ID=?");
								stat3.setInt(1, bookingID);
								ResultSet result3=stat3.executeQuery();
								int total=0;
								int paid=0;
								while(result3.next()) {
									total=result3.getInt(1);
									paid=result3.getInt(2);
								}
								if(total==paid) {
									PreparedStatement stat2 = con.prepareStatement("UPDATE HALL.BOOKING_DETAILS SET STATUS='REFUNDED' WHERE BOOKING_ID=? AND STATUS='BOOKED'");
									reAssign(bookingID,"Cancelled");
									stat2.setInt(1,bookingID);
									stat2.execute();
									System.out.println(Colors.GREEN+"          --- Your Amount is SuccessFully Refunded ---"+Colors.RESET);
								}else {
									System.out.println(Colors.RED+"          AS YOU CHOOSED INITAL PAYMENT WE CANNOT REFUND YOU :("+Colors.RESET);
								}
								
							}else {
								System.out.println(Colors.RED+"          You Need To Pay Before You get Your REFUND"+Colors.RESET);
							}
							break;
						default :
							break;
						}
						
					}catch(Exception e) {
						System.out.println(e.getMessage());
						val='y';
					}
				}while(val=='y' && cnt>0);
				
				
			}else {
				PreparedStatement stat = con.prepareStatement("SELECT b.BOOKING_ID,b.RENTING_DAYS,b.HALL_ID,b.STATUS,h.HALL_NAME,h.PRICE_PER_DAY FROM HALL.BOOKING_DETAILS b JOIN HALL.HALLS h ON b.HALL_ID=h.HALL_ID WHERE b.USER_ID IN (SELECT USER_ID FROM HALL.USERS WHERE USER_NAME=? AND PASSWORD=?) AND STATUS <> 'CANCELLED'");
				stat.setString(1, userName);
				stat.setString(2, password);
				
				ResultSet result = stat.executeQuery();
				
				System.out.println("          +--------------------------------------------------------------------------------------+");
				System.out.println("          |  "+Colors.YELLOW+"BOOKING_ID "+Colors.RESET+" |  "+Colors.YELLOW+"RENTING_DAYS"+Colors.RESET+"  |  "+Colors.YELLOW+"HALL_ID"+Colors.RESET+"  |  "+Colors.YELLOW+"STATUS"+Colors.RESET+"  |  "+Colors.YELLOW+"HALL_NAME"+Colors.RESET+"  |  "+Colors.YELLOW+"PRICE_PER_DAY"+Colors.RESET+"  | ");
				System.out.println("          +--------------------------------------------------------------------------------------+");
				while (result.next()) {
				    int bookingId = result.getInt(1);
				    int rentingDays = result.getInt(2);
				    String hallId = result.getString(3);
				    String status = result.getString(4);
				    String hallName = result.getString(5);
				    String pricePerDay = result.getString(6);

				    System.out.println("          |  " + String.format("%-12s", bookingId) +
				                       "  |  " + String.format("%-14s", rentingDays) +
				                       "  |  " + String.format("%-8s", hallId) +
				                       "  |  " + String.format("%-8s", status) +
				                       "  |  " + String.format("%-10s", hallName) +
				                       "  |  " + String.format("%-15s", pricePerDay) +
				                       "  |");
				}
				System.out.println("          +--------------------------------------------------------------------------------------+");
				
				System.out.print("\n          Enter the Bookind ID :");
				int cnt=3;
				char val='y';
				do {
					try { cnt--;
						int bookingID=Integer.parseInt(sc.readLine());
						System.out.println(Colors.GREEN+"          Choose From the Option :"+Colors.RESET);
						System.out.println("               1.Cancel Booking");
						System.out.println("               2.Refund Amount");
						System.out.print(Colors.YELLOW+"\n          Enter You Choice : "+Colors.RESET);
						int choice=Integer.parseInt(sc.readLine());
						switch(choice) {
						case 1:
							cancelBooking(bookingID);
							System.out.println(Colors.GREEN+"          --- Your Request Accepeted CANCELLED"+Colors.RESET);
							break;
						case 2:
							System.out.println(Colors.GREEN+"          --- VALIDATING YOUR REQUEST --- "+Colors.RESET);
							PreparedStatement stat1=con.prepareStatement("SELECT STATUS FROM HALL.BOOKING_DETAILS WHERE BOOKING_ID=?");
							stat1.setInt(1, bookingID);
							ResultSet result1=stat1.executeQuery();
							int count=0;
							String status="";
							while(result1.next()) {
								count++;
								status=result1.getString(1);
							}
							if(status.equalsIgnoreCase("Booked")) {
								PreparedStatement stat3 = con.prepareStatement("SELECT TOTAL_AMOUNT,PAID_AMOUNT FROM HALL.PAYMENTS WHERE BOOKING_ID=?");
								stat3.setInt(1, bookingID);
								ResultSet result3=stat3.executeQuery();
								int total=0;
								int paid=0;
								while(result3.next()) {
									total=result3.getInt(1);
									paid=result3.getInt(2);
								}
								if(total==paid) {
									PreparedStatement stat2 = con.prepareStatement("UPDATE HALL.BOOKING_DETAILS SET STATUS='REFUNDED' WHERE BOOKING_ID=? AND STATUS='BOOKED'");
									stat2.setInt(1,bookingID);
									stat2.execute();
									System.out.println(Colors.GREEN+"          --- Your Amount is SuccessFully Refunded ---"+Colors.RESET);
								}else {
									System.out.println(Colors.RED+"          AS YOU CHOOSED INITAL PAYMENT WE CANNOT REFUND YOU :("+Colors.RESET);
								}
								
							}else {
								System.out.println(Colors.RED+"          You Need To Pay Before You get Your REFUND"+Colors.RESET);
							}
							break;
						default :
							break;
						}
						
					}catch(Exception e) {
						System.out.println(e.getMessage());
						val='y';
					}
				}while(val=='y' && cnt>0);
				
				
			}
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void reAssign(int bookingID,String status) {
		Connection con = ConnectionDb.Connect();
		try {
			PreparedStatement stat = con.prepareStatement("SELECT RENTING_DAYS,HALL_ID FROM HALL.BOOKING_DETAILS WHERE BOOKING_ID=?");
			stat.setInt(1, bookingID);
			int rentingDays=0;
			String hallID="";
			ResultSet result = stat.executeQuery();
			while(result.next()) {
				rentingDays=result.getInt(1);
				hallID=result.getString(2);
			}
			
			PreparedStatement stat1 = con.prepareStatement("SELECT DAYS FROM HALL.HALLS WHERE HALL_ID=?");
			stat1.setString(1, hallID);
			ResultSet result1 = stat1.executeQuery();
			int totalDays=0;
			while(result1.next()) {
				totalDays=result1.getInt(1);
			}
			
			if(status.equalsIgnoreCase("Approved")) {
				totalDays-=rentingDays;
				if(totalDays<=0) {
					totalDays=rentingDays+1;
				}
			}else {
				totalDays+=rentingDays;
			}
			
			PreparedStatement stat2 =con.prepareStatement("UPDATE HALL.HALLS SET DAYS=? WHERE HALL_ID=?");
			stat2.setInt(1, totalDays);
			stat2.setString(2, hallID);
			stat2.execute();
			
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getDays() {
		return days;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public String getHallID() {
		return hallID;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public void setHallID(String hallID) {
		this.hallID = hallID;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}
	
	
	
}
