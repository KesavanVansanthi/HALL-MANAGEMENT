package com.DriverPackage;
import com.Exceptions.InputInvalidException;
import com.enums.CATEGORY;
import com.threads.ReassignDays;
import com.users.Admin;
import com.users.Customers;
import com.users.HallOwner;
import com.validation.*;
import java.io.*;
import java.util.Locale.Category;

/**
 * 
 * 
 * 
 */

public class Guest {
	public static void main(String[] args) throws NumberFormatException, IOException, InputInvalidException {
		BufferedReader sc = new  BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("\n\n "+
				Colors.CYAN+"               ██|     ██| ████  █|      ■|███   ■███■   ██■   ■██  ████\n "+Colors.RESET
			   +Colors.CYAN+"               ██|     ██| █|    █|     ██      ██   ██  █| | █ |█  █|  \n "+Colors.RESET
			   +Colors.CYAN+"                █  ▏▏▏▏▏▏▏▏▏▏▏▏▏▏▏▏▏▏▏▏  █|  ███   █|     ██      ██   ██  █| |█  |█  ███ \n "+Colors.RESET
			   +Colors.CYAN+"                █ █  █ █|  █|    █|     ██      ██   ██  █|     |█  █|  \n "+Colors.RESET
			   +Colors.CYAN+"                ██|   ██|  ████  █████   ■|███   ■███■   █|     |█  ████\n "+Colors.RESET);
		
		System.out.println(Colors.CYAN+"                                           TO                      "+Colors.RESET);
		System.out.println("                     **********************************************");
		System.out.println("                     *   "+Colors.LAVENDER+"    --- HALL MANAGEMENT SYSTEM ---    "+Colors.RESET+"   *");
		System.out.println("                     **********************************************");
		System.out.println();
		System.out.println("          +--------------------------------------------------------------------+");
		System.out.println("          |  "+Colors.RED+" ABOUT US :      "+Colors.RESET+"                                                 |");
		System.out.println("          |          Here,We provide a service to book Halls, Events and       |\n          |      you can also rent your Halls through our service...           |");
		System.out.println("          +--------------------------------------------------------------------+");
		
		char choice='y';
		do {
			try {
				System.out.println("          +--------------------------------------------------------------------+");
				System.out.println("          |       "+Colors.GREEN+"           ### CHOOSE YOUR OPTION ###           "+Colors.RESET+"             |");
				System.out.println("          +--------------------------------------------------------------------+");
				System.out.println("          |                          1.ADMIN                                   |");
				System.out.println("          |                          2.CUSTOMER                                |");
				System.out.println("          |                          3.HALL OWNER                              |");
				System.out.println("          |                          4.Exit                                    | ");
				System.out.println("          +--------------------------------------------------------------------+");
				System.out.print(Colors.YELLOW+"\n          Your Option (1/2/3/4) : "+Colors.RESET);
				char option=sc.readLine().charAt(0);
				char go='y';
				switch(option) {
				case '1':
					do {
						System.out.println("\n               --- PRESS '1' to "+Colors.PURPLE+"LOGIN"+Colors.RESET+" ---\n");
						System.out.println("                        +-------+  ");
						System.out.println("                        |"+Colors.PURPLE+" LOGIN "+Colors.RESET+"|  ");
						System.out.println("                        +-------+  ");
						System.out.print(Colors.YELLOW+"          Choice : "+Colors.RESET);
						char value=sc.readLine().charAt(0);
						switch(value) {
						case '1':
							go='n';
							new Login("Admin");
							break;
						default :
							try {
								throw new InputInvalidException(Colors.RED+"          --- Invalid Choice ---"+Colors.RESET);
							}catch(Exception e) {
								System.out.println(e.getMessage());
							}
							
						}
					}while(go=='y');
					
					break;
				case '2':
					
					do {
						System.out.println("\n               --- PRESS '1' to"+Colors.PURPLE+" LOGIN "+Colors.RESET+"---\n");
						System.out.println("\n                        +-------+  ");
						System.out.println("                        |"+Colors.PURPLE+" LOGIN "+Colors.RESET+"|  ");
						System.out.println("                        +-------+             ");
						System.out.println("\n                                             +----------+ ");
						System.out.println("          Don't have an account? then '2' -> | "+Colors.GREEN+"REGISTER"+Colors.RESET+" | ");
						System.out.println("                                             +----------+ ");
						System.out.print(Colors.YELLOW+"          Choice : "+Colors.RESET);
						char value=sc.readLine().charAt(0);
						switch(value) {
						case '1':
							go='n';
							new Login("Customer");
							break;
						case '2':
							go='n';
							new Register();
							break;
						default :
							try {
								throw new InputInvalidException(Colors.RED+"          --- Invalid Choice ---"+Colors.RESET);
							}catch(Exception e) {
								System.out.println(e.getMessage());
							}
						}
					}while(go=='y');
					break;
				case '3':
					do {
						System.out.println("\n               --- PRESS '1' to"+Colors.PURPLE+" LOGIN "+Colors.RESET+"---\n");
						System.out.println("\n                        +-------+  ");
						System.out.println("                        |"+Colors.PURPLE+" LOGIN "+Colors.RESET+"|  ");
						System.out.println("                        +-------+             ");
						System.out.println("\n                                             +----------+ ");
						System.out.println("          Don't have an account? then '2' -> | "+Colors.GREEN+"REGISTER"+Colors.RESET+" | ");
						System.out.println("                                             +----------+ ");
						System.out.print(Colors.YELLOW+"          Choice : "+Colors.RESET);
						char value=sc.readLine().charAt(0);
						switch(value) {
						case '1':
							go='n';
							new Login("hallowner");
							break;
						case '2':
							go='n';
							new Register();
							break;
						default :
							try {
								throw new InputInvalidException(Colors.RED+"          --- Invalid Choice ---"+Colors.RESET);
							}catch(Exception e) {
								System.out.println(e.getMessage());
							}
						}
					}while(go=='y');
					break;
				case '4':
					choice='n';
					break;
				default :
					throw new InputInvalidException(Colors.RED+"          --- Invalid Choice ---"+Colors.RESET);
				}
				
				if(go=='n') {
					System.out.print(Colors.YELLOW+"          if you only want to"+Colors.RESET+Colors.PURPLE+" LOG OUT"+Colors.RESET+" -> ("+Colors.PURPLE+"y"+Colors.RESET+") "+Colors.RED+"Exit"+Colors.RESET+" ->("+Colors.RED+"n"+Colors.RESET+") : ");
					char val =sc.readLine().charAt(0);
					switch(val) {
					case 'y':
						choice='y';
						break;
					case 'n':
						choice='n';
						break;
					default :
						try {
							throw new InputInvalidException(Colors.RED+"          --- Invalid Choice ---"+Colors.RESET);
						}catch(Exception e) {
							System.out.println(e.getMessage());
						}
					}
					
				}
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}while(choice=='y');
		
		System.out.println("\n                            -------------------------");
		System.out.println(Colors.GREEN+"                               Thanks For Visiting   "+Colors.RESET);
		System.out.println("                            -------------------------");
	}

}
