package com.events;

import java.io.*;
import java.sql.SQLException;

import com.DriverPackage.Colors;
import com.EventDatabase.CommunityData;
import com.Exceptions.InputInvalidException;

public class CommunityEvent {
	public CommunityEvent() {
		
	}
	public CommunityEvent(String userName,String password,String category) {
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		char val='y';
		int count=3;
		do {
			count--;
			System.out.println("\n          Our Community Hall Services Could be Useful for Conducting :-");
			System.out.println("\n          ************************************************************");
			System.out.println("          *                    --- "+Colors.YELLOW+"Marraiges"+Colors.RESET+" ---                     *");
			System.out.println("          *                   --- "+Colors.YELLOW+"Family Meets"+Colors.RESET+" ---                   *");
			System.out.println("          *                 --- "+Colors.YELLOW+"Birthday Parties"+Colors.RESET+" ---                 *");
			System.out.println("          ************************************************************");

			System.out.print(Colors.YELLOW+"\n          Do you want to see Available Services ?(y/n) : "+Colors.RESET);
			try {
				char choice = sc.readLine().charAt(0);
				if(choice=='y') {
					CommunityData.viewDetails(userName,password,category);
				}else if(choice=='n') {
					System.out.println(Colors.RED+"\n          --- Press 'y' to choose the Service --- "+Colors.RESET);
				}else {
					try {
						throw new InputInvalidException(Colors.RED+"\n          --- Invalid Choice ---"+Colors.RESET);
					}catch(Exception e) {
						System.out.println(e.getMessage());
					}
				}
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}while(val=='y' && count>0);
		
	}
	
//	public void showServices(String userName,String password) throws IOException, InputInvalidException, SQLException{
//		
//	}
}
