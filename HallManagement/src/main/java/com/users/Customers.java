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
	
	public static void viewHallService(String userName,String password,String category) throws InputInvalidException {
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		char val='y';
		do {
			try {
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
						throw new InputInvalidException(Colors.RED+"\n          --- Invalid Choice ---"+Colors.RESET);
				}
			}catch(Exception e)
			{
				System.out.println(e);
				System.out.print(Colors.YELLOW+"\n          Do you want to book again ?(y/n) : "+Colors.RESET);
				try {
					val=sc.readLine().charAt(0);
				}catch(Exception E)
				{
					System.out.println(E);
				}
			}
			
		}while(val=='y');
		
		
	}

}

//sc.close();