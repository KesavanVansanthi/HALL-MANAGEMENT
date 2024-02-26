package com.users;

import java.io.*;
import java.sql.SQLException;

import com.DriverPackage.Colors;
import com.EventDatabase.AdminBookData;
import com.EventDatabase.BookHall;
import com.EventDatabase.PaymentData;
import com.Exceptions.HallInvalidException;
import com.Exceptions.InputInvalidException;
import com.Exceptions.InvalidBookingException;
import com.HallDatabase.Hall;
import com.HallDatabase.UpdateHall;
import com.enums.Request;
import com.events.BusinessEvent;
import com.events.CommunityEvent;

/**
 * The `Admin` class represents an administrator user of the Hall Management System.
 * It provides methods for viewing halls, validating booking requests, and updating hall information.
 */

public class Admin extends Users {
	
	private String userID;
	private String password;
	
	private Hall hall;
	
	private BookHall book;
	
	public String getUserID() {
		return userID;
	}

	public String getPassword() {
		return password;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public BookHall getBook() {
		return book;
	}

	public void setBook(BookHall book) {
		this.book = book;
	}
	
	 /**
     * Default constructor for the `Admin` class.
     */

	public Admin() {
		
	}
	
    /**
     * Constructor for creating a new `Admin` object with the specified details.
     *
     * @param userName The user name of the admin
     * @param password The password of the admin
     * @param category The category of the admin
     * @throws SQLException if there is an error in the SQL query
     */

	public Admin(String userName,String password,String category) throws SQLException{
		super(userName,password,category);
		
	}	
	
	 /**
     * Displays the hall booking options for the admin.
     *
     * @param userName The user name of the admin
     * @param password The password of the admin
     * @param category The category of the admin
     */
	
	public static void viewHall(String userName,String password,String category){
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		char val='y';
		int count=3;
		do {
			try {
				count=3;
				do {
					count--;
					System.out.println();
					System.out.println("\n          *****************************************");
					System.out.println("          *       "+Colors.GREEN+"--- CHOOSE YOUR EVENTS"+Colors.RESET+" ---      *");
					System.out.println("          *****************************************\n");
					System.out.println("                 1.Community Event");
					System.out.println("                 2.Business Event");
					System.out.print(Colors.YELLOW+"\n          Your Choice : "+Colors.RESET);
					int choice =Integer.parseInt(sc.readLine());
					switch(choice){
						case 1:
							new CommunityEvent(userName,password,category);
							System.out.print(Colors.YELLOW+"\n          Do you want to book again ?(y/n) : "+Colors.RESET);
							val=sc.readLine().charAt(0);
							break;
						case 2:
							new BusinessEvent(userName,password,category);
							System.out.print(Colors.YELLOW+"\n          Do you want to book again ?(y/n) : "+Colors.RESET);
							val=sc.readLine().charAt(0);
							break;
						default :
							val='y';
							try {
								throw new InputInvalidException(Colors.RED+"\n          --- Invalid Choice ---"+Colors.RESET);
							}catch(Exception e) {
								System.out.println(e.getMessage());
							}
					}
				}while(val=='y' && count>0);
				
			}catch(Exception e)
			{
				System.out.println(e.getMessage());
				System.out.print(Colors.YELLOW+"\n          Do you want to continue?(y/n)"+Colors.RESET);
				try {
					val=sc.readLine().charAt(0);
				}catch(Exception E)
				{
					System.out.println(E.getMessage());
				}
			}
			
		}while(val=='y');
	}
	
	  /**
     * Validates a hall booking request.
     *
     * @param hall     The hall object
     * @param userName The user name of the admin
     * @param password The password of the admin
     * @param event    The event type
     * @throws HallInvalidException   if the hall is invalid
     * @throws IOException            if there is an I/O error
     * @throws SQLException           if there is an error in the SQL query
     */
	
	public void requesValidate(Hall hall,String userName,String password,int event) throws HallInvalidException, IOException, SQLException {
		this.setHall(hall);
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		System.out.println(Colors.GREEN+"          "+Request.VALIDATING+" your REQUEST..."+Colors.RESET);
		if(this.getHall().getPrice()>=40000) {
			if(!(this.getHall().getAc()=='y')) {
				throw new HallInvalidException(Colors.RED+"\n          Your Hall Price is Too High for a Non-Ac Hall"+Colors.RESET+"\n                --- please Enter a Valid price ---");
				
			}
			else {
				if(!(this.getHall().getInterest()<=70)) {
					throw new HallInvalidException(Colors.RED+"\n          Your Interest Percent is way too High than our Policy"+Colors.RESET+"\n             --- Please Enter Less than "+Colors.GREEN+"70 percent"+Colors.RESET+" ---");
				}
				else {
					System.out.print(Colors.GREEN+"\n               Your Request "+Request.ACCEPTED+"!!!!"+Colors.RESET);
					
					hall.insertHall(this.getHall(),userName,password,event);
					
					System.out.println(Colors.ORANGE+"                #####   HALL ADDED :)   #####"+Colors.RESET);
				}
			}
		}
		else {
			System.out.println(Colors.YELLOW+"          Do you entered a Right Price ?(y/n) : "+Colors.RESET);
			char choice = sc.readLine().charAt(0);
			if(choice=='y') {
				if(!(this.getHall().getInterest()<=70)) {
					throw new HallInvalidException(Colors.RED+"\n          Your Interest Percent is way too High than our Policy"+Colors.RESET+"\n             --- Please Enter Less than "+Colors.GREEN+"70 percent"+Colors.RESET+" ---");
				}
				else {
					System.out.print(Colors.GREEN+"\n               Your Request "+Request.ACCEPTED+"!!!!"+Colors.RESET);
					
					hall.insertHall(this.getHall(),userName,password,event);
					System.out.println(Colors.ORANGE+"                #####   HALL ADDED :)   #####"+Colors.RESET);
				}
			}
			else {
				System.out.println(Colors.RED+"           --- Please try Again with the Pricing ---"+Colors.RESET);
			}
		}
		
	}
	
	public Hall getHall() {
		return hall;
	}

	public void setHall(Hall hall) {
		this.hall = hall;
	}
	
	 /**
     * Updates hall information based on the specified choice and new value.
     *
     * @param choice The choice of information to update
     * @param newValue The new value for the information
     * @param hallID The ID of the hall
     * @throws SQLException if there is an error in the SQL query
     */
	
	public static void updateHall(int choice,Object newValue,String hallID) throws SQLException {
		
		switch(choice) {
		case 1:
			String hallName = (String)newValue;
			UpdateHall.updateName(hallName,hallID);
			break;
		case 2:
			int room = (int)newValue;
			UpdateHall.updateRoom(room,hallID);
			break;
		case 3:
			int days = (int)newValue;
			UpdateHall.updateDays(days,hallID);
			break;
		case 4:
		    char ac	= (char)newValue;
		    UpdateHall.updateAc(ac,hallID);
			break;
		case 5:
			int price = (int)newValue;
			UpdateHall.updatePrice(price,hallID);
			break;
		case 6:
			int interest = (int)newValue;
			UpdateHall.updateInterest(interest,hallID);
			break;
		case 7:
			String event = (String)newValue;
			UpdateHall.updateEvent(event,hallID);
			break;
		}
		
	}
	
	/**
     * Verifies a hall booking request.
     *
     * @param userName The user name of the admin
     * @param password The password of the admin
     * @throws InvalidBookingException if the booking is invalid
     */
	
	public static void verifyBooking(String userName,String password) throws InvalidBookingException {
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		AdminBookData.pendingBooking();
		
		try {
			System.out.print(Colors.YELLOW+"\n          Enter the Booking ID to Validate : "+Colors.RESET);
			int BookingID=Integer.parseInt( sc.readLine());
			boolean result = AdminBookData.verifyBooking(BookingID);
			if(result) {
				System.out.print("\n          Mark it as Approved ? (y/n) : "+Colors.RESET);
				char approve=sc.readLine().charAt(0);
				if(approve=='y') {
					PaymentData.addPayment(userName,password,BookingID);
					BookHall.reAssign(BookingID,"Approved");
					String status="Approved";
					AdminBookData.finalstatus(status,BookingID);
					System.out.println(Colors.GREEN+"\n          --- $$$ UPDATED $$$ ---"+Colors.RESET);
					System.out.println(Colors.ORANGE+"\n          !!! CUSTOMER WILL GET NOTIFY !!!"+Colors.RESET);
				}
				else {
					throw new InputInvalidException(Colors.ORANGE+"\n          !!! CUSTOMER IS WAITING FOR YOUR APPROVAL !!!"+Colors.RESET);
				}
				
				
			}else {
				System.out.print(Colors.YELLOW+"\n          Mark it as Rejected ? (y/n) : "+Colors.RESET);
				char approve=sc.readLine().charAt(0);
				if(approve=='y') {
					BookHall.reAssign(BookingID,"Rejected");
					String status="Rejected";
					AdminBookData.finalstatus(status,BookingID);
					System.out.println(Colors.GREEN+"\n          --- $$$ UPDATED $$$ ---"+Colors.RESET);
					System.out.println(Colors.ORANGE+"\n          !!! CUSTOMER WILL GET NOTIFY !!!"+Colors.RESET);
				}
				else {
					throw new InputInvalidException(Colors.ORANGE+"\n          !!! CUSTOMER IS WAITING FOR YOUR APPROVAL !!!"+Colors.RESET);
				}
				
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());

		}
		
		
	}

}
