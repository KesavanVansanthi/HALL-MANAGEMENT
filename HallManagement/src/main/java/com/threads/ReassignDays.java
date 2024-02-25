package com.threads;

import java.sql.Connection;
import java.util.*;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

import com.DriverPackage.Colors;
import com.databaseConnection.ConnectionDb;

public class ReassignDays extends Thread {
	private String userName;
	private String password;
	private String category;
	private Date date;
	private int rentingDays;
	private int ID;
	private int days;
	private boolean running;
	
	
	public Date getDate() {
		return date;
	}

	public int getRentingDays() {
		return rentingDays;
	}

	public int getID() {
		return ID;
	}

	public int getDays() {
		return days;
	}

	public boolean isRunning() {
		return running;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setRentingDays(int rentingDays) {
		this.rentingDays = rentingDays;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public String getCategory() {
		return category;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	

	
	
	public ReassignDays(Date date, int rentingDays, int iD) {
		this.setDate(date);
		this.setRentingDays(rentingDays);
		this.setID(iD);
	}
	
	
	public ReassignDays() {
		
	}

	public void run() {
		Connection con=ConnectionDb.Connect();
		try {
			if(this.getCategory().equalsIgnoreCase("Admin")) {
				
				Thread.sleep(1000);
				System.out.print(Colors.CYAN+"\n          Validting Your Account"+Colors.RESET);
				int count=5;
				while(count>0) {
					count--;
					Thread.sleep(1000);
					System.out.print(Colors.CYAN+"."+Colors.RESET);
				}
				
				List<ReassignDays> lists = new ArrayList<>();
				
				PreparedStatement stat = con.prepareStatement("SELECT END_DATE,RENTING_DAYS,BOOKING_ID FROM HALL.BOOKING_DETAILS WHERE ADMIN_ID=(SELECT ADMIN_ID FROM HALL.ADMINS WHERE USER_NAME=? AND PASSWORD=?) AND STATUS IN ('APPROVED','PENDING', 'BOOKED')");
				stat.setString(1, this.getUserName());
				stat.setString(2, this.getPassword());
				
				ResultSet result = stat.executeQuery();
				while(result.next()) {
					date=result.getDate(1);
					rentingDays=result.getInt(2);
					ID=result.getInt(3);
					
					lists.add(new ReassignDays(date,rentingDays,ID));
					
				}
				
				for(ReassignDays list : lists) {
					
					LocalDate localDate = LocalDate.now(); // Example LocalDate

			        LocalDate endDate = list.getDate().toLocalDate();
					if (endDate.isBefore(localDate)) {
			        	PreparedStatement stat1 = con.prepareStatement("SELECT DAYS FROM HALL.HALLS WHERE HALL_ID=(SELECT HALL_ID FROM HALL.BOOKING_DETAILS WHERE BOOKING_ID=?)");
			        	stat1.setInt(1, ID);
			        	ResultSet result1 = stat1.executeQuery();
			        	while(result1.next()) {
			        		days=result1.getInt(1);
			        	}
			        	PreparedStatement stat2 = con.prepareStatement("UPDATE HALL.HALLS SET DAYS=? WHERE HALL_ID=(SELECT HALL_ID FROM HALL.BOOKING_DETAILS WHERE booking_ID=?)");
			        	stat2.setInt(1, days+rentingDays);
			        	stat2.setInt(2, list.getID());
						
						stat2.execute();
						
						
						PreparedStatement stat3 = con.prepareStatement("UPDATE HALL.BOOKING_DETAILS SET STATUS='OVER' WHERE BOOKING_ID=?");
						stat3.setInt(1, list.getID());
						stat3.execute();
						
						
						Thread.sleep(1000);
			        	System.out.print(Colors.CYAN+"\n          UPDATING YOUR PROFILE"+Colors.RESET);
			        	count=5;
						while(count>0) {
							count--;
							Thread.sleep(1000);
							System.out.print(Colors.CYAN+"."+Colors.RESET);
						}
						System.out.println(Colors.GREEN+"\n                 --- YOUR ACCOUNT UPDATED ---"+Colors.RESET);
			        }
				}
				Thread.sleep(500);
		        System.out.println(Colors.GREEN+"\n          --- VALIDATION COMPLETED ---"+Colors.RESET);
		         
			}else {
				
				Thread.sleep(1000);
				System.out.print(Colors.CYAN+"\n          Validting Your Account"+Colors.RESET);
				int count=5;
				while(count>0) {
					count--;
					Thread.sleep(1000);
					System.out.print(Colors.CYAN+"."+Colors.RESET);
				}
				
				List<ReassignDays> lists = new ArrayList<>();
				
				PreparedStatement stat = con.prepareStatement("SELECT END_DATE,RENTING_DAYS,BOOKING_ID FROM HALL.BOOKING_DETAILS WHERE USER_ID=(SELECT USER_ID FROM HALL.USERS WHERE USER_NAME=? AND PASSWORD=?) AND STATUS IN ('APPROVED','PENDING', 'BOOKED')");
				stat.setString(1, this.getUserName());
				stat.setString(2, this.getPassword());
				
				ResultSet result = stat.executeQuery();
				while(result.next()) {
					date=result.getDate(1);
					rentingDays=result.getInt(2);
					ID=result.getInt(3);
					
					lists.add(new ReassignDays(date,rentingDays,ID));
					
				}
				
				for(ReassignDays list : lists) {
					
					LocalDate localDate = LocalDate.now(); // Example LocalDate

			        LocalDate endDate = list.getDate().toLocalDate();
					if (endDate.isBefore(localDate)) {
			        	PreparedStatement stat1 = con.prepareStatement("SELECT DAYS FROM HALL.HALLS WHERE HALL_ID=(SELECT HALL_ID FROM HALL.BOOKING_DETAILS WHERE BOOKING_ID=?)");
			        	stat1.setInt(1, ID);
			        	ResultSet result1 = stat1.executeQuery();
			        	while(result1.next()) {
			        		days=result1.getInt(1);
			        	}
			        	PreparedStatement stat2 = con.prepareStatement("UPDATE HALL.HALLS SET DAYS=? WHERE HALL_ID=(SELECT HALL_ID FROM HALL.BOOKING_DETAILS WHERE booking_ID=?)");
			        	stat2.setInt(1, days+rentingDays);
			        	stat2.setInt(2, list.getID());
						
						stat2.execute();
						
						PreparedStatement stat3 = con.prepareStatement("UPDATE HALL.BOOKING_DETAILS SET STATUS='OVER' WHERE BOOKING_ID=?");
						stat3.setInt(1, list.getID());
						stat3.execute();
						
						Thread.sleep(1000);
			        	System.out.print(Colors.CYAN+"\n          UPDATING YOUR PROFILE"+Colors.RESET);
			        	count=5;
						while(count>0) {
							count--;
							Thread.sleep(1000);
							System.out.print(Colors.CYAN+"."+Colors.RESET);
						}
						System.out.println(Colors.GREEN+"\n                 --- YOUR ACCOUNT UPDATED ---"+Colors.RESET);
			        }
				}
				Thread.sleep(500);
		        System.out.println(Colors.GREEN+"\n          --- VALIDATION COMPLETED ---"+Colors.RESET);
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	 public void stopThread() {
	        running = false;
	        interrupt();
	    }

	
}
