package com.Booking;

import java.io.*;

import com.DriverPackage.Colors;
import com.EventDatabase.BookHall;
import com.EventDatabase.PaymentData;
import com.Exceptions.InputInvalidException;

public class Payment {
	
	private int bookingID;
	private int total;
	
	public Payment() {
		
	}
	public Payment(int bookingID) {
		this.setBookingID(bookingID);
		int totalAmount=PaymentData.totalAmount(bookingID);
		this.setTotal(totalAmount);
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		System.out.println(Colors.LAVENDER+"\n           --- WE ARE PROVIDING ' 2 ' OPTIONS --- "+Colors.RESET);
		System.out.println("\t          1.Initial Payment  ");
		System.out.println("\t          2.Full Payment  ");
		char val='y';
		do {
			try {
				System.out.print(Colors.YELLOW+"\n          Enter your Will : "+Colors.RESET);
				int choice = Integer.parseInt(sc.readLine());
				char ch='n';
				switch(choice) {
				case 1:
					val='n';
					ch=initialPayment();
					if(ch=='y') {
						new PaymentData(this.getBookingID(),choice,this.getTotal());
						
					}else {
						System.out.println(Colors.YELLOW+"          --- You need to pay to get Confirmed ---"+Colors.RESET);
					}
					break;
				case 2:
					val='n';
					ch=fullPayment();
					if(ch=='y') {
						System.out.println("as");
						new PaymentData(this.getBookingID(),choice,this.getTotal());
						
					}else {
						System.out.println(Colors.YELLOW+"          --- You need to pay to get Confirmed ---"+Colors.RESET);
					}
					break;
				default :
					val='y';
					throw new InputInvalidException(Colors.RED+"          --- Invalid Input ---"+Colors.RESET);
				}
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}
			
		}while(val=='y');
		
	}
	public char fullPayment() {
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("\n          --------------------------------------------");
		System.out.println(Colors.LAVENDER+"                 You Have Choosed FULL Payment"+Colors.RESET);
		System.out.println("          --------------------------------------------");
		char ch='n';
		System.out.println(Colors.YELLOW+"            Note : "+Colors.RESET);
		System.out.println("                By Our Policy "+Colors.YELLOW+"-- >"+Colors.RESET+" FULL Payment should be paid \n              100% of the Original price per Day And refund is possible");
		char choice='y';
		do {
			try {
				System.out.print("\n          Are You Okay With it ? (y/n) : ");
				choice=sc.readLine().charAt(0);
				switch(choice) {
				case 'y':
					choice='n';
					int total=BookHall.paymentDetails(this.getBookingID());
					this.setTotal(total);
					System.out.println("\n          -----------------------------------------");
					System.out.println(Colors.YELLOW+"                   Total Amount : "+Colors.RESET+this.getTotal());
					System.out.println("          -----------------------------------------");
					System.out.println(Colors.YELLOW+"\n             As You Choosed FULL Payment :-"+Colors.RESET);
					System.out.println("\n          *********************************");
					System.out.println("           -:  "+Colors.YELLOW+"You need to Pay : "+Colors.RESET+Colors.GREEN+this.getTotal()+Colors.RESET+" :-");
					System.out.println("          *********************************");
					System.out.print(Colors.YELLOW+"\n          Shall we Proceed to the Payment ? (y/n) : "+Colors.RESET);
					ch =sc.readLine().charAt(0);
					break;
				case 'n':
					choice='n';
					break;
				default :
					choice='y';
					throw new InputInvalidException(Colors.RED+"          --- Invalid Choice ----"+Colors.RESET);
					
				}
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}
			
		}while(choice=='y');
		return ch;
	}
	
	public char initialPayment() {
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("\n          --------------------------------------------");
		System.out.println(Colors.LAVENDER+"                 You Have Choosed Initial Payment"+Colors.RESET);
		System.out.println("          --------------------------------------------");
		char ch='n';
		System.out.println(Colors.YELLOW+"            Note : "+Colors.RESET);
		System.out.println("                By Our Policy"+Colors.YELLOW+" -- > "+Colors.RESET+"Initial Payment should be paid \n            35% of the Original price per Day,Remaining Amount can be settled to the Hall Owner \n                     And Refund is not Possible");
		char choice='y';
		do {
			try {
				System.out.print("\n          Are You Okay With it ? (y/n) : ");
				choice=sc.readLine().charAt(0);
				switch(choice) {
				case 'y':
					choice='n';
					int total=BookHall.paymentDetails(this.getBookingID());
					this.setTotal(total);
					System.out.println("\n          -----------------------------------------");
					System.out.println(Colors.YELLOW+"                   Total Amount : "+Colors.RESET+this.getTotal());
					System.out.println("          -----------------------------------------");
					System.out.println("\n          **********************************");
					System.out.println("           -: "+Colors.YELLOW+" You need to Pay : "+Colors.RESET+this.getTotal()*0.35+" :-");
					System.out.println("          **********************************");
					System.out.print(Colors.YELLOW+"\n          Shall we Proceed to the Payment ? (y/n) : "+Colors.RESET);
					ch =sc.readLine().charAt(0);
					break;
				case 'n':
					choice='n';
					break;
				default :
					choice='y';
					throw new InputInvalidException(Colors.RED+"          --- Invalid Choice ----"+Colors.RESET);
					
				}
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}
			
		}while(choice=='y');
		return ch;
	}
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getBookingID() {
		return bookingID;
	}
	public void setBookingID(int bookingID) {
		this.bookingID = bookingID;
	}
	
}
