package com.users;
import java.io.*;
import java.sql.SQLException;

import com.DriverPackage.Colors;
import com.Exceptions.InputInvalidException;
import com.events.*;


public class Customers extends Users {
	
	public Customers() {
		
	}
	
	public Customers(String userName,String password,String category) throws SQLException{
		super(userName,password,category);
	}
	
	/**
     * Allows the customer to view and choose events to book.
     * 
     * @param userName The username of the customer.
     * @param password The password of the customer.
     * @param category The category of the customer.
     * @throws InputInvalidException If the input is invalid.
     */
	
	public static void viewHallService(String userName,String password,String category) throws InputInvalidException {
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		char val='y';
		int count =3;
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
							//Takes to the Community event
							new CommunityEvent(userName,password,category);
							System.out.print(Colors.YELLOW+"\n          Do you want to book again ?(y/n) : "+Colors.RESET);
							val=sc.readLine().charAt(0);
							break;
						case 2:
							//To business Event
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
				System.out.println(e);
				System.out.print(Colors.YELLOW+"\n          Do you want to book again ?(y/n) : "+Colors.RESET);
				try {
					val=sc.readLine().charAt(0);
				}catch(Exception E)
				{
					System.out.println(E.getMessage());
				}
			}
			
		}while(val=='y');
		
		
	}

}

//sc.close();