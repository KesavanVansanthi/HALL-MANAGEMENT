package com.Booking;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.DriverPackage.Colors;
import com.EventDatabase.BookHall;
import com.EventDatabase.BusinessData;
import com.EventDatabase.CommunityData;
import com.Exceptions.InputInvalidException;
import com.databaseConnection.ConnectionDb;
import com.enums.Request;
import com.users.HallOwner;

public class Book {
	LocalDate startDate;
	LocalDate endDate;
	
	private String hallID;
	private String userName;
	private String password;
	private String category;
	
	public Book() {
		
	}
	public Book(String hallID,String userName,String password,String category) throws InputInvalidException, SQLException {
		
		this.setHallID(hallID);
		this.setUserName(userName);
		this.setPassword(password);
		
		Connection con = ConnectionDb.Connect();
		
		PreparedStatement stat = con.prepareStatement("SELECT EVENT FROM HALL.HALLS WHERE HALL_ID=?");
		stat.setString(1,hallID);
		ResultSet result = stat.executeQuery();
		String event="";
		while(result.next()) {
			event=result.getString(1);
		}
		
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		System.out.println(Colors.GREEN+"\n                ### Enter the Below Details to BOOK ### "+Colors.RESET);
		System.out.println("\n           -------------------------------------------------");
		int validDay=0;
		int days;
		try {
			char val='y';
			int count=3;
			do {
				System.out.print(Colors.YELLOW+"\n          How many Days you want to rent the Hall : "+Colors.RESET);
				days = Integer.parseInt(sc.readLine());
				PreparedStatement stat2 = con.prepareStatement("SELECT DAYS FROM HALL.HALLS WHERE HALL_ID=?");
				stat2.setString(1, this.getHallID());
				ResultSet result2=stat2.executeQuery();
				int totalDays=0;
				while(result2.next()) {
					totalDays=result.getInt(1);
				}
				if(!(totalDays>days)) {
					val='y';
					try {
						throw new InputInvalidException(Colors.RED+"\n          --- Days Should be Less than Available Days ---"+Colors.RESET);
					}catch(Exception e) {
						System.out.println(e.getMessage());
					}
				}else {
					val='n';
				}
				count--;
			}while(val=='y' && count>0);
			
			if(event.equalsIgnoreCase("COMMUNITY EVENT")) {
				validDay = CommunityData.validDays(hallID,days);
			}else {
				validDay = BusinessData.validDays(hallID,days);
			}
			
			if(!(validDay==0)) {
				throw new InputInvalidException(Colors.RED+"          ERROR : Your days should be less than "+validDay+Colors.GREEN);
			}else {
				if(days>=1) {
					System.out.println(Colors.GREEN+"\n          --- Enter the Dates --- "+Colors.RESET);
					System.out.print(Colors.YELLOW+"          Start Date (MM/DD/YYYY) : "+Colors.RESET);
					String stDate=sc.readLine();
					startDate = LocalDate.parse(stDate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
					endDate = startDate.plusDays(days);
						
				}else {
					System.out.println(Colors.GREEN+"\n          --- Enter the Date --- "+Colors.RESET);
					System.out.print(Colors.YELLOW+"          Start Date (MM/DD/YYYY) : "+Colors.RESET);
					String stDate=sc.readLine();
					startDate = LocalDate.parse(stDate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
					endDate=startDate;
				}
			}
			BookHall book = new BookHall(days,startDate,endDate,this.getHallID(),this.getUserName(),this.getPassword(),event);
			book.insertdetails(category);
			System.out.println(Colors.YELLOW+"           --- Your Booking were "+Request.REQUESTED+" ---"+Colors.RESET);
			System.out.println(Colors.GREEN+"\n           *** Please visit Sometimes later ***"+Colors.RESET);
			
			
		} catch (NumberFormatException | IOException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	public void viewBook(String userName,String password,String category) {
		this.setUserName(userName);
		this.setPassword(password);
		this.setCategory(category);
		
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		String status="";
		
		try {
			System.out.println(Colors.LAVENDER+"\n          --- ### VIEWING BOOKING DETAILS ### ---"+Colors.RESET);
			List<String> messages=BookHall.fetchBooking(this.getUserName(),this.getPassword(),this.getCategory());
			for(String message : messages) {
				status="";
				String[] msg=message.split(" ");
				int bookingID = Integer.parseInt(msg[1]);
				String statusIn = msg[0];
				
				if(statusIn.equalsIgnoreCase("approved")) {
					
					System.out.println("\n          **************************************");
					System.out.println(Colors.YELLOW+"               ###  BOOKING ID : "+Colors.RESET+bookingID+Colors.YELLOW+"  ###"+Colors.RESET);
					System.out.println(Colors.YELLOW+"                   ###  STATUS : "+Colors.RESET+statusIn.toUpperCase()+Colors.YELLOW+"  ###"+Colors.RESET);
					System.out.println("          **************************************");
					System.out.println(Colors.GREEN+"\n                --- Your Request Has Approved :) ---"+Colors.RESET);
					
				}else if(statusIn.equalsIgnoreCase("Rejected")) {
					
					System.out.println("\n          **************************************");
					System.out.println(Colors.YELLOW+"               ###  BOOKING ID : "+Colors.RESET+bookingID+Colors.YELLOW+"  ###"+Colors.RESET);
					System.out.println(Colors.YELLOW+"                   ###  STATUS : "+Colors.RESET+statusIn.toUpperCase()+Colors.YELLOW+"  ###"+Colors.RESET);
					System.out.println("          **************************************");
					System.out.println(Colors.RED+"\n                --- OOP'S YOUR REQUEST GOT DELICNED :( ---"+Colors.RESET);
					System.out.println(Colors.RESET+"\n          Note : "+Colors.RESET+"This Booking will be Deleted ");
					BookHall.deleteBook(bookingID);
					
				}else if(statusIn.equalsIgnoreCase("Booked")) {
					
					System.out.println("\n          **************************************");
					System.out.println(Colors.YELLOW+"               ###  BOOKING ID : "+Colors.RESET+bookingID+Colors.YELLOW+"  ###"+Colors.RESET);
					System.out.println(Colors.YELLOW+"                   ###  STATUS : "+Colors.RESET+statusIn.toUpperCase()+Colors.YELLOW+"  ###"+Colors.RESET);
					System.out.println("          **************************************");
					System.out.println(Colors.CYAN+"\n                --- Already Booked Id ---"+Colors.RESET);
				} else if(statusIn.equalsIgnoreCase("Refunded")){
					
					System.out.println("\n          **************************************");
					System.out.println(Colors.YELLOW+"               ###  BOOKING ID : "+Colors.RESET+bookingID+Colors.YELLOW+"  ###"+Colors.RESET);
					System.out.println(Colors.YELLOW+"                   ###  STATUS : "+Colors.RESET+statusIn.toUpperCase()+Colors.YELLOW+"  ###"+Colors.RESET);
					System.out.println("          **************************************");
					System.out.println(Colors.BLUE+"\n                --- Booking Refunded ---"+Colors.RESET);
					
				} else if(statusIn.equalsIgnoreCase("Cancelled")){
					
					System.out.println("\n          **************************************");
					System.out.println(Colors.YELLOW+"               ###  BOOKING ID : "+Colors.RESET+bookingID+Colors.YELLOW+"  ###"+Colors.RESET);
					System.out.println(Colors.YELLOW+"                   ###  STATUS : "+Colors.RESET+statusIn.toUpperCase()+Colors.YELLOW+"  ###"+Colors.RESET);
					System.out.println("          **************************************");
					System.out.println(Colors.RED+"\n                --- Booking Cancelled ---"+Colors.RESET);
				}else {

					System.out.println("\n          **************************************");
					System.out.println(Colors.YELLOW+"               ###  BOOKING ID : "+Colors.RESET+bookingID+Colors.YELLOW+"  ###"+Colors.RESET);
					System.out.println(Colors.YELLOW+"                   ###  STATUS : "+Colors.RESET+statusIn.toUpperCase()+Colors.YELLOW+"  ###"+Colors.RESET);
					System.out.println("          **************************************");
					
					System.out.println(Colors.ORANGE+"\n         --- Admin Is Not Responded Yet. Please, Visit Sometimes Later ---"+Colors.RESET);
				}
			}
			
			Connection con = ConnectionDb.Connect();
				System.out.print(Colors.YELLOW+"\n          SHALL WE MOVE TO PAYMENT ? (y/n) : "+Colors.RESET);
				char choice=sc.readLine().charAt(0);
				if(choice=='y') {
					System.out.println("\n             ------------------------------------");
					System.out.print(Colors.YELLOW+"                  Enter the Booking ID to Pay : "+Colors.RESET);
					int bookingID=Integer.parseInt(sc.readLine());
					System.out.println("             ------------------------------------");
					
					char ch='y';
					PreparedStatement id = con.prepareStatement("SELECT BOOKING_ID FROM HALL.BOOKING_DETAILS WHERE BOOKING_ID=?");
					id.setInt(1, bookingID);
					ResultSet result1=id.executeQuery();
					int count=0;
					while(result1.next()) {
						count++;
					}
					if(count>0) {
						PreparedStatement stat =con.prepareStatement("SELECT STATUS FROM HALL.BOOKING_DETAILS WHERE BOOKING_ID=?");
						stat.setInt(1, bookingID);
						ResultSet result=stat.executeQuery();
						while(result.next()) {
							status=result.getString(1);
						}
						if(status.equalsIgnoreCase("Approved")) {
							do {
								switch(choice) {
								case 'y':
									ch='n';
									new Payment(bookingID);
									break;
								case 'n':
									ch='n';
									System.out.print(Colors.YELLOW+"          Are You willing Cancel Your Booking ? (y/n) : "+Colors.RESET);
									char cancel=sc.readLine().charAt(0);
									switch(cancel) {
									case 'y':
										BookHall.cancelBooking(bookingID);
										break;
									case 'n':
										System.out.print(Colors.YELLOW+"          Are you willing to Continue booking ? (y/n) : "+Colors.RESET);
										if(sc.readLine().charAt(0)=='y') {
											new Payment(bookingID);
										}else {
											ch='n';
										}
										break;
									default :
										throw new InputInvalidException(Colors.RED+"          --- Invalid Choice ---"+Colors.RESET);
									}
									break;
								default :
									ch='y';
									throw new InputInvalidException(Colors.RED+"\n          --- Invalid Input ---"+Colors.RESET);	
								}
							}while(ch=='y');
						}else if(status.equalsIgnoreCase("Rejected")){
							System.out.println(Colors.RED+"\n           --- SORRY YOUR CANNOT PAY AS YOUR REQUEST GOT REJECTED ---"+Colors.RESET);
							do {
								System.out.print(Colors.YELLOW+"          Are You Willing to Book Again ? (y/n) : "+Colors.RESET);
								char will=sc.readLine().charAt(0);
								switch(will) {
								case 1:
									ch='n';
									HallOwner.viewHallService(this.getUserName(),this.getPassword(),this.getPassword());
									break;
								case 2:
									ch='n';
									break;
								default :
									ch='y';
									throw new InputInvalidException(Colors.RED+"\n          --- Invalid Choice ---"+Colors.RESET);
								}
							}while(ch=='y');
							
						}else if(status.equalsIgnoreCase("Booked")) {
							System.out.println(Colors.RED+"\n           --- YOU CANNOT BOOK THE BOOKED HALL --- "+Colors.RESET);
						}else if(status.equalsIgnoreCase("pending")) {
							System.out.println(Colors.YELLOW+"\n           --- HEY YOU ARE STILL NEED TO WAIT UNTIL THE ADMIN VERIFIES YOUR REQUEST"+Colors.RESET);
						}
					}else {
						throw new InputInvalidException(Colors.RED+"          --- Booking ID Not Exist ---`"+Colors.RESET);
					}
					
					
				}else {
					
					System.out.println(Colors.RED+"          ---  If You Need To Pay then Choose 'y' Next Time ---"+Colors.RESET);
				}
		}catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		
		
	}
	
	public static void viewMyHall(String userName,String password) {
		Connection con = ConnectionDb.Connect();
		try {
			PreparedStatement stat = con.prepareStatement("SELECT h.HALL_ID, h.HALL_NAME, h.ADDRESS, h.ROOMS, h.DAYS, h.AC_NONAC, h.PRICE_PER_DAY, h.INTEREST, h.EVENT ,b.STATUS,b.RENTING_DAYS,b.USER_ID FROM HALL.HALLS h JOIN HALL.BOOKING_DETAILS b ON h.HALL_ID = b.HALL_ID WHERE h.HALLOWNER_ID = (SELECT HALLOWNER_ID FROM HALL.HALLOWNERS WHERE USER_NAME=? AND PASSWORD=?) AND b.STATUS='APPROVED'");
			stat.setString(1, userName);
			stat.setString(2, password);
			ResultSet result = stat.executeQuery();
			System.out.println("-------------------------------------------------------------------------------------------------------");
            System.out.printf("   | %-15s | %-15s | %-7s | %-7s | %-10s | %-15s | %-15s%n",
            		Colors.YELLOW+"HALL_ID"+Colors.RESET,Colors.YELLOW+"HALL_NAME"+Colors.RESET, Colors.YELLOW+"ROOMS"+Colors.RESET, Colors.YELLOW+"DAYS"+Colors.RESET, Colors.YELLOW+"AC_NONAC"+Colors.RESET, Colors.YELLOW+"PRICE_PER_DAY"+Colors.RESET, Colors.YELLOW+"ADDRESS"+Colors.RESET);
            while (result.next()) {
                System.out.printf("   | %-15s | %-15s | %-7s | %-7s | %-10s | %-15s | %-15s%n",
                		result.getString("HALL_ID"),
                		result.getString("HALL_NAME"),
                		result.getString("ROOMS"),
                		result.getString("DAYS"),
                		result.getString("AC_NONAC"),
                		result.getString("PRICE_PER_DAY"),
                		result.getString("ADDRESS"));
            }
            System.out.println("-------------------------------------------------------------------------------------------------------");

		}catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getHallID() {
		return hallID;
	}
	public void setHallID(String hallID) {
		this.hallID = hallID;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public String getUserName() {
		return userName;
	}
	public String getPassword() {
		return password;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
