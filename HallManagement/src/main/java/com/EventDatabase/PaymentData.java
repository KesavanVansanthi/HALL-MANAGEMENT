package com.EventDatabase;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.Booking.Payment;
import com.DriverPackage.Colors;
import com.Exceptions.InputInvalidException;
import com.databaseConnection.ConnectionDb;
import com.userdetaildatabase.HallOwnerDetailsDatabase;
import com.userdetails.HallOwnerDetails;

public class PaymentData {
	
	private int amount;
	private double percent;
	private int bookingID;
	
	public PaymentData() {
		
	}
	public PaymentData(int bookingID,int choice,int amount) {
		this.setBookingID(bookingID);
		this.setAmount(amount);
		if(choice==1) {
			this.setPercent(0.35);
		}else {
			this.setPercent(1.0);
		}
		
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		if(choice==1) {
			char ch='y';
			do {
				try {
					System.out.println("\n             +---------------------------------------+");
					System.out.println("             | "+Colors.GREEN+"--- Enter the Mode Of Transaction ---"+Colors.RESET+" |");
					System.out.println("             +---------------------------------------+");
					System.out.println("             |              1.Gpay                   |"
							         + "\n             |              2.Credit Card            |"
							         + "\n             |              3.Onsite Payment         |"
							         + "\n             +---------------------------------------+");
					System.out.print(Colors.YELLOW+"\n          Enter Your Choice : "+Colors.RESET);
					int mode =Integer.parseInt(sc.readLine());
					switch(mode) {
					case 1:
						ch='n';
						gPay();
						break;
					case 2:
						ch='n';
						creditCard();
						break;
					case 3:
						ch='n';
						onSite();
						break;
					default :
						ch='y';
						throw new InputInvalidException(Colors.RED+"          --- Invalid Choice ---"+Colors.RESET);
					}
				}catch(Exception e) {
					System.out.println(e.getMessage());
				}
			}while(ch=='y');
			
		}else if(choice==2) {
			char ch='y';
			do {
				try {
					System.out.println("\n             +---------------------------------------+");
					System.out.println("             | "+Colors.GREEN+"--- Enter the Mode Of Transaction ---"+Colors.RESET+" |");
					System.out.println("             +---------------------------------------+");
					System.out.println("             |              1.Gpay                   |"
							         + "\n             |              2.Credit Card            |"
							         + "\n             |              3.Onsite Payment         |"
							         + "\n             +---------------------------------------+");
					System.out.print(Colors.YELLOW+"\n          Enter Your Choice : "+Colors.RESET);
					int mode =Integer.parseInt(sc.readLine());
					switch(mode) {
					case 1:
						ch='n';
						gPay();
						break;
					case 2:
						ch='n';
						creditCard();
						break;
					case 3:
						ch='n';
						onSite();
						break;
					default :
						ch='y';
						throw new InputInvalidException(Colors.RED+"          --- Invalid Choice ---"+Colors.RESET);
					}
				}catch(Exception e) {
					System.out.println(e.getMessage());
				}
			}while(ch=='y');
		}
	}
	
	public void gPay() {
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.println("\n            +-------------------------------------------+");
			System.out.println("            |   "+Colors.GREEN+"  ### Enter the Payment Details ###  "+Colors.RESET+"   |");
			System.out.println("            +-------------------------------------------+");
			char val='y';
			do{
				System.out.print("\n                "+Colors.YELLOW+"   UPI id : "+Colors.RESET);
				String upi=sc.readLine();
				Boolean matches =Pattern.matches("^\\w+@\\w+$", upi);
	    		if(!matches) {
	    			val='y';
	    			throw new InputInvalidException("          --- Invalid UPI ---");
	    		}else {
	    			val='n';
	    			pay(this.getBookingID());
	    		}
	    		
			}while(val=='y');
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
	}
	public void creditCard() {
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		
        try {
        	System.out.println("\n            +-------------------------------------------+");
			System.out.println("            |   "+Colors.GREEN+"  ### Enter the Payment Details ###  "+Colors.RESET+"   |");
			System.out.println("            +-------------------------------------------+");
			char val='y';
			do{
				System.out.print(Colors.YELLOW+"\n                   Card Number : "+Colors.RESET);
				String cardNo = sc.readLine();
				Boolean matches =Pattern.matches("^\\d{16}$", cardNo);
				if(!matches) {
					val='y';
					throw new InputInvalidException(Colors.RED+"          --- Invalid Card Number ---"+Colors.RESET);
				}else {
					val='n';
					pay(this.getBookingID());
				}
			}while(val=='y');
		}catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} 
		
		
	}
	public void onSite() {
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			System.out.println(Colors.ORANGE+"          -: Please Contact The HallOwner For Payments :-"+Colors.RESET);
			HallOwnerDetailsDatabase.contactHallOwnerDetails(this.getBookingID());
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	public void pay(int bookingID) {
		this.setBookingID(bookingID);
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.println(Colors.YELLOW+"\n                  Total Amount : "+Colors.RESET+this.getAmount()*this.getPercent());
			System.out.print(Colors.GREEN+"\n          Confirm to Pay (y/n) : "+Colors.RESET);
			char choice=sc.readLine().charAt(0);
			switch(choice) {
			case 'y':
				Connection con = ConnectionDb.Connect();
				try {
					PreparedStatement stat = con.prepareStatement("INSERT INTO HALL.PAYMENTS (TOTAL_AMOUNT,PAID_AMOUNT,PAID_STATUS,BOOKING_ID) VALUES (?,?,'PAID',?)");
					stat.setDouble(1, this.getAmount());
					stat.setDouble(2, this.getAmount()*this.getPercent());
					stat.setInt(3, this.getBookingID());
					
					PreparedStatement upt = con.prepareStatement("UPDATE HALL.BOOKING_DETAILS SET STATUS='BOOKED' WHERE BOOKING_ID=?");
					upt.setInt(1, bookingID);
					
					upt.execute();
					stat.execute();
					
				}catch(Exception e){
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
				System.out.println(Colors.GREEN+"\n          --- PAYMENT SUCCESSFULL ---"+Colors.RESET);
				break;
			case 'n':
				System.out.print(Colors.YELLOW+"\n          Are You willing Cancel Your Booking ? (y/n) : "+Colors.RESET);
				char cancel=sc.readLine().charAt(0);
				switch(cancel) {
				case 'y':
					BookHall.cancelBooking(bookingID);
					break;
				case 'n':
					System.out.print(Colors.YELLOW+"\n          Are you willing to Continue booking ? (y/n) : "+Colors.RESET);
					if(sc.readLine().charAt(0)=='y') {
						new Payment(bookingID);
					}else {
						throw new InputInvalidException(Colors.RED+"\n          --- SORRY YOU CANNOT CHOOSE NO AGAIN ---"+Colors.RESET);
					}
				}
				break;
			default :
				throw new InputInvalidException(Colors.RED+"\n          --- Invalid Choice ---"+Colors.RESET);
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		
	}
	
	public static int totalAmount(int bookingID) {
		Connection con=ConnectionDb.Connect();
		int totalAmount=0;
		int days=1;
		try {
			PreparedStatement stat = con.prepareStatement("SELECT h.PRICE_PER_DAY,b.RENTING_DAYS FROM HALL.HALLS h JOIN HALL.BOOKING_DETAILS b ON h.HALL_ID = b.HALL_ID WHERE b.HALL_ID IN (SELECT HALL_ID FROM HALL.BOOKING_DETAILS WHERE BOOKING_ID=?)");
			stat.setInt(1, bookingID);
			ResultSet result = stat.executeQuery();
			while(result.next()) {
				totalAmount=result.getInt(1);
				days=result.getInt(2);
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return totalAmount*days;
		
	}
	
	public static void addPayment(String userName,String password,int bookingID){
		Connection con=ConnectionDb.Connect();
		try {
			int totalAmount=0;
			int days=0;
			PreparedStatement stats=con.prepareStatement("SELECT h.PRICE_PER_DAY,b.RENTING_DAYS FROM HALL.HALLS h JOIN HALL.BOOKING_DETAILS b ON h.HALL_ID = b.HALL_ID WHERE b.HALL_ID IN (SELECT HALL_ID FROM HALL.BOOKING_DETAILS WHERE BOOKING_ID=?)");
			stats.setInt(1, totalAmount);
			
			ResultSet result = stats.executeQuery();
			while(result.next()) {
				totalAmount=result.getInt(1);
				days=result.getInt(2);
			}
			PreparedStatement stat = con.prepareStatement("INSERT INTO HALL.PAYMENTS (TOTAL_AMOUNT,PAID_AMOUNT,PAID_STATUS,BOOKING_ID) VALUES (?,10,'NOT PAID',?)");
			stat.setInt(1,totalAmount*days);
			stat.setInt(2, bookingID);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public double getPercent() {
		return percent;
	}
	public void setPercent(double d) {
		this.percent = d;
	}
	public void setChoice(double choice) {
		
		if(choice==1) {
			this.setPercent(0.35);
		}else {
			this.setPercent(1);
		}
	}
	public int getBookingID() {
		return bookingID;
	}
	public void setBookingID(int bookingID) {
		this.bookingID = bookingID;
	}
	
	
	
}
