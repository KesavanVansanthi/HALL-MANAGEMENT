package com.DriverPackage;
import com.Exceptions.InputInvalidException;
import com.validation.*;
import java.io.*;

/**
 * This class represents the main entry point for the Hall Management System. 
 * It provides options for administrators, customers, and hall owners to login 
 * or register, and manages the flow of the application based on user input.
 * 
 * @author Kesavan Saravanan Expleo
 * 
 */

public class Guest {
    /**
     * The main method of the application.
     * It displays a welcome message and presents options for different user roles.
     *
     * @throws NumberFormatException    if there is an error converting a string to a number
     * @throws IOException              if there is an I/O error
     * @throws InputInvalidException    if the user enters an invalid input
     */
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
				int count=3;
				switch(option) {
				case '1':
					count=3;
					do {
						count--;
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
						
					}while(go=='y' && count>0);
					
					break;
				case '2':
					count=3;
					do {
						count--;
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
					}while(go=='y' && count>0);
					break;
				case '3':
					count=3;
					do {
						count--;
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
					}while(go=='y' && count>0);
					break;
				case '4':
					choice='n';
					break;
				default :
					try {
						throw new InputInvalidException(Colors.RED+"          --- Invalid Choice ---"+Colors.RESET);
					}catch(Exception e) {
						System.out.println(e.getMessage());
					}
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
