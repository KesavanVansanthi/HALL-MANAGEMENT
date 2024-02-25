package com.validation;
import com.DriverPackage.Colors;
import com.database.UserLogin;
import com.enums.CATEGORY;
import com.threads.ReassignDays;
import com.users.*;
import java.io.*;
import java.sql.SQLException;

public class Login {
	
	private String category;

	public Login(String category) throws IOException {
		this.setCategory(category);
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		char value='y';
		do {
			try{
				System.out.println("\n          ****************************************");
				System.out.println("          *        "+Colors.LAVENDER+"  ---- LOGIN HERE ----   "+Colors.RESET+"     *");
				System.out.println("          ****************************************");
				System.out.println(Colors.GREEN+"\n                 ---- Enter you Details --- "+Colors.RESET);
				System.out.println("          ----------------------------------------");

				System.out.print(Colors.YELLOW+"\n          User ID : "+Colors.RESET);
				String userID =sc.readLine();
				System.out.print(Colors.YELLOW+"          Password : "+Colors.RESET);
				String password =sc.readLine();
				
				UserLogin userlogin = new UserLogin();
				boolean result = userlogin.validate(userID,password,this.getCategory());
				
				if(result) {
					if("ADMIN".equalsIgnoreCase(this.getCategory())) {
						value='n';
						System.out.println(Colors.LAVENDER+"\n                    You are LOGGED IN as "+CATEGORY.ADMIN+"!!!"+Colors.RESET);
						ReassignDays rd=new ReassignDays();
						rd.setCategory(category);
						rd.setPassword(password);
						rd.setUserName(userID);
						rd.start();
						rd.join();
						rd.stopThread();
						new Admin(userID,password,this.getCategory());
					}
					else if("HallOwner".equalsIgnoreCase(this.getCategory())) {
						value='n';
						System.out.println(Colors.LAVENDER+"\n                    You are LOGGED IN as "+CATEGORY.HALLOWNER+"!!!"+Colors.RESET);
						ReassignDays rd=new ReassignDays();
						rd.setCategory(category);
						rd.setPassword(password);
						rd.setUserName(userID);
						rd.start();
						rd.join();
						rd.stopThread();
						new HallOwner(userID,password,this.getCategory());
						
					}else if("Customer".equalsIgnoreCase(this.getCategory())){
						value='n';
						System.out.println(Colors.LAVENDER+"\n                    You are LOGGED IN as "+CATEGORY.CUSTOMER+"!!!"+Colors.RESET);
						ReassignDays rd=new ReassignDays();
						rd.setCategory(category);
						rd.setPassword(password);
						rd.setUserName(userID);
						rd.start();
						rd.join();
						new Customers(userID,password,this.getCategory());
						rd.stopThread();
					}
				}
				else {
					System.out.println(Colors.RED+"\n          --- Please Enter a valid details ---"+Colors.RESET);
					System.out.print(Colors.YELLOW+"\n          Do you want to continue (y/n): "+Colors.RESET);
					value=sc.readLine().charAt(0);
				}
			}catch(Exception e) {
				System.out.println(e);
			}
			
		}while(value=='y');

	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}


}

