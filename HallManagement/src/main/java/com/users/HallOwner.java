package com.users;
import java.io.*;
import java.sql.SQLException;

import com.DriverPackage.Colors;
import com.Exceptions.HallInvalidException;
import com.Exceptions.InputInvalidException;
import com.HallDatabase.Hall;
import com.enums.Request;
import com.events.BusinessEvent;
import com.events.CommunityEvent;
import com.events.Concerts;

/**
 * The `HallOwner` class represents a hall owner user of the Hall Management System.
 * It provides methods for viewing hall services, adding halls, viewing and updating owned halls.
 */

public class HallOwner extends Users {
	String Events;
	BusinessEvent Business;
	
	public HallOwner() {
		
	}
	
	  /**
     * Constructor for creating a new `HallOwner` object with the specified details.
     *
     * @param userName The user name of the hall owner
     * @param password The password of the hall owner
     * @param category The category of the hall owner
     * @throws SQLException if there is an error in the SQL query
     */
	
	public HallOwner(String userName,String password,String category) throws SQLException {
		super(userName,password,category);
	}
	
	 /**
     * Displays the hall services for the hall owner to choose from.
     *
     * @param userName The user name of the hall owner
     * @param password The password of the hall owner
     * @param category The category of the hall owner
     */
	
	public static void viewHallService(String userName, String password,String category) {
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
	
    /**
     * Adds a new hall for the hall owner.
     *
     * @param userName The user name of the hall owner
     * @param password The password of the hall owner
     * @throws HallInvalidException if the hall is invalid
     */
	
	public void addHall(String userName,String password)throws HallInvalidException {
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.println(Colors.GREEN+"\n                  Enter the Details about the Hall : "+Colors.RESET);
			System.out.print(Colors.YELLOW+"          Enter Your Hall ID : "+Colors.RESET);
			String hallID=sc.readLine();
			boolean valid=Hall.isValidID(hallID);
			if(valid) {
				throw new HallInvalidException(Colors.RED+"          Hall_ID Already Exist...Please Enter a different ID!!!"+Colors.RESET);
			}
			System.out.print(Colors.YELLOW+"\n          Hall Name : "+Colors.RESET);
			String hallName=sc.readLine();
			System.out.print(Colors.YELLOW+"          Address : "+Colors.RESET);
			String address=sc.readLine();
			System.out.print(Colors.YELLOW+"          Number of Rooms : "+Colors.RESET);
			int room=Integer.parseInt(sc.readLine());
			System.out.print(Colors.YELLOW+"          available days in week : "+Colors.RESET);
			final int days=Integer.parseInt(sc.readLine());
			System.out.print(Colors.YELLOW+"          AC or Non-AC (y/n) : "+Colors.RESET);
			char ac=sc.readLine().charAt(0);
			System.out.print(Colors.YELLOW+"          Price Per Day : "+Colors.RESET);
			int price=Integer.parseInt(sc.readLine());
			System.out.print(Colors.YELLOW+"          Rent Interest Exceptation (%) :"+Colors.RESET);
			int interest=Integer.parseInt(sc.readLine());
			System.out.print(Colors.YELLOW+"\n          Is your Hall Is Appilicable for  the Below Events : "+Colors.RESET+
								"\n            1.Community Event \n            2.Business Events \n");
			System.out.print(Colors.ORANGE+"          If So, Enter their Number (1/2) : "+Colors.RESET);
			
			int choice =Integer.parseInt(sc.readLine());
			if(choice==2) {
				Events="BUSINESS EVENT";
				if(ac=='y') {
					BusinessEvent business = new BusinessEvent();
					Business=business.addEvent();
				}
				else {
					throw new InputInvalidException(Colors.RED+"          --- SORRY BUSSINESS EVENT HALLS SHOULD HAVE AC SERVICES ---"+Colors.RESET);
				}
			}else if(choice==1) {
				Events="COMMUNITY EVENT";		
			}
			System.out.println(Colors.GREEN+"          --- "+Request.REQUESTED+" to ADD the Hall ---"+Colors.RESET);
			Hall hall = new Hall(hallID,hallName,address,room,days,ac,price,interest,Events,Business);
			Admin admin = new Admin();
			if(Events.equalsIgnoreCase("Community Event")) {
				admin.requesValidate(hall,userName,password,1);
			}else {
				admin.requesValidate(hall,userName,password,2);
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	 /**
     * Views and manages owned halls for the hall owner.
     *
     * @param userName The user name of the hall owner
     * @param password The password of the hall owner
     * @throws NumberFormatException if a number format is invalid
     * @throws IOException           if there is an I/O error
     * @throws HallInvalidException  if the hall is invalid
     * @throws SQLException          if there is an error in the SQL query
     */
	
	public void viewHall(String userName,String password) throws NumberFormatException, IOException, HallInvalidException, SQLException {
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		System.out.println(Colors.GREEN+"                   Your Available Halls : "+Colors.RESET);
		Hall.viewUserHall(userName,password);
		System.out.println(Colors.YELLOW+"\n          Enter the HALL ID : "+Colors.RESET);
		String hallID=sc.readLine();
		char val='y';
		do {
			System.out.println(Colors.YELLOW+"          Chooce Option : "+Colors.RESET);
			System.out.println("             1.Update \n             2.Delete");
			try {
				int choice=Integer.parseInt(sc.readLine());
				switch(choice) {
				case 1:
					val='n';
					updateHall(hallID);
					break;
				case 2:
					val='y';
					deleteHall(hallID);
					break;
				default :
					throw new HallInvalidException(Colors.RED+"          --- Invalid Option ---"+Colors.RESET);
				}
			}catch(Exception e) {
				val='y';
				System.out.println(e.getMessage());
			}
		}while(val=='y');
		
		
		
		
		
		
	}
	
	 /**
     * Deletes a hall owned by the hall owner.
     *
     * @param hallID The ID of the hall to delete
     * @throws IOException  if there is an I/O error
     * @throws SQLException if there is an error in the SQL query
     */
	
	public static void deleteHall(String hallID) throws IOException, SQLException {
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		System.out.println(Colors.YELLOW+"          Do you Want to Delete Hall : "+hallID+" ?(y/n) :"+Colors.RESET);
		char delete =sc.readLine().charAt(0);
		if(delete=='y') {
			System.out.println(Colors.GREEN+"\t --- Requested to Delete the HAll---"+Colors.RESET);
			Hall.deleteHall(hallID);
		}
		
		
	}
	
	 /**
     * Updates information of a hall owned by the hall owner.
     *
     * @param hallID The ID of the hall to update
     * @throws NumberFormatException if a number format is invalid
     * @throws IOException           if there is an I/O error
     * @throws HallInvalidException  if the hall is invalid
     * @throws SQLException          if there is an error in the SQL query
     */
	
	public void updateHall(String hallID) throws NumberFormatException, IOException, HallInvalidException, SQLException {
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		System.out.println(Colors.YELLOW+"\n          What you want to Update ?"+Colors.RESET);
		System.out.println("\n           1.HALL NAME        2.NUMBER OF ROOMS");
		System.out.println("           3.DAYS	          4.AC_NONAC	      5.PRICE_PER_DAY");
		System.out.println("           6.INTEREST         7.Event");
		System.out.println(Colors.YELLOW+"\n          Your Choice : "+Colors.RESET);
		int choice =Integer.parseInt(sc.readLine());
		switch(choice) {
		case 1:
			System.out.print(Colors.YELLOW+"          Enter New Hall Name :"+Colors.RESET);
			String hallName = sc.readLine();
			Admin.updateHall(choice,hallName,hallID);
			break;
		case 2:
			System.out.print(Colors.YELLOW+"          Enter New No Of Rooms :"+Colors.RESET);
			int room =Integer.parseInt(sc.readLine());
			Admin.updateHall(choice,room,hallID);
			break;
		case 3:
			System.out.print(Colors.YELLOW+"          New Available Days :"+Colors.RESET);
			int days =Integer.parseInt(sc.readLine());
			Admin.updateHall(choice,days,hallID);
			break;
		case 4:
			System.out.print(Colors.YELLOW+"          New System -> (Ac or Non-AC) :"+Colors.RESET);
			char ac =sc.readLine().charAt(0);
			Admin.updateHall(choice,ac,hallID);
			break;
		case 5:
			System.out.print(Colors.YELLOW+"          Enter New Price/Day :"+Colors.RESET);
			int price =Integer.parseInt(sc.readLine());
			if(price<40000) {
				System.out.print(Colors.YELLOW+"\n          Do you entered a Right Price ?(y/n) : "+Colors.RESET);
				char ch = sc.readLine().charAt(0);
				if(!(ch=='y')) {
					throw new HallInvalidException(Colors.RED+"\n           --- Please try Again with the Pricing ---"+Colors.RESET);
				}
			}else {
				Admin.updateHall(choice,price,hallID);
			}
			break;
		case 6:
			System.out.print(Colors.YELLOW+"          Enter New Interest :"+Colors.RESET);
			int interest =Integer.parseInt(sc.readLine());
			if(!(interest<=70)) {
				throw new HallInvalidException("          --- Intrest Should be"+Colors.RED+" Less than 70 Percent "+Colors.RESET+"---");
			}else {
				Admin.updateHall(choice,interest,hallID);
			}
			break;
		case 7:
			System.out.print(Colors.YELLOW+"          Enter New Event :"+Colors.RESET);
			String event=sc.readLine();
			Admin.updateHall(choice,event,hallID);
			break;
		default :
			throw new HallInvalidException(Colors.RED+"          --- Invalid Choice to Update ---"+Colors.RESET);
		}
	}
	
	
}
