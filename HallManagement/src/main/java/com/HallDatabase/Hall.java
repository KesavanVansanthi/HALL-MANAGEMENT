package com.HallDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.DriverPackage.Colors;
import com.databaseConnection.ConnectionDb;
import com.events.BusinessEvent;

public class Hall {
	private String hallID;
	private String hallName;
	private String address;
	private int room;
	private int days;
	private char ac;
	private int price;
	private int interest;
	private String Events;
	
	private BusinessEvent business;
	
	public Hall() {
		
	}
	
	public Hall(String hallID,String hallName,String address,int room,int days,char ac,int price,int interest,String Events,BusinessEvent business){
		this.setAc(ac);
		this.setAddress(address);
		this.setDays(days);
		this.setEvents(Events);
		this.setHallID(hallID);
		this.setHallName(hallName);
		this.setInterest(interest);
		this.setPrice(price);
		this.setRoom(room);
		this.setBusiness(business);
	}
	public void insertHall(Hall hall,String urName,String pass,int choice){
		
		Connection con=ConnectionDb.Connect();
		
		PreparedStatement stat1;
		try {
			stat1 = con.prepareStatement("SELECT HALLOWNER_ID FROM HALL.HALLOWNERS WHERE USER_NAME=? AND PASSWORD=?");
			stat1.setString(1, urName);
			stat1.setString(2, pass);
			
			ResultSet result =stat1.executeQuery();
			int hallownerID=0;
			while(result.next()) {
				hallownerID=result.getInt(1);
			}
			if(ac=='y') {
				if(choice==1) {
					final String Query_value ="INSERT INTO HALL.HALLS(HALL_ID,HALL_NAME,ADDRESS,ROOMS,DAYS,AC_NONAC,PRICE_PER_DAY,INTEREST,EVENT,HALLOWNER_ID)VALUES(?,?,?,?,?,'AC',?,?,?,?)";
					PreparedStatement stat = con.prepareStatement(Query_value);
					stat.setString(1, this.getHallID());
					stat.setString(2, this.getHallName());
					stat.setString(3, this.getAddress());
					stat.setInt(4, this.getRoom());
					stat.setInt(5, this.getDays());
					stat.setInt(6, this.getPrice());
					stat.setInt(7, this.getInterest());
					stat.setString(8, this.getEvents());
					stat.setInt(9, hallownerID);
					
					stat.execute();
				}else {
					final String Query_value ="INSERT INTO HALL.HALLS(HALL_ID,HALL_NAME,ADDRESS,ROOMS,DAYS,AC_NONAC,PRICE_PER_DAY,INTEREST,EVENT,HALLOWNER_ID)VALUES(?,?,?,?,?,'AC',?,?,?,?)";
					PreparedStatement stat = con.prepareStatement(Query_value);
					stat.setString(1, this.getHallID());
					stat.setString(2, this.getHallName());
					stat.setString(3, this.getAddress());
					stat.setInt(4, this.getRoom());
					stat.setInt(5, this.getDays());
					stat.setInt(6, this.getPrice());
					stat.setInt(7, this.getInterest());
					stat.setString(8, this.getEvents());
					stat.setInt(9, hallownerID);
					
					stat.execute();
					
					final String Query_Bussiness="INSERT INTO HALL.BUSINESSEVENT VALUES(?,?,?,?,?,?)";
					PreparedStatement statB=con.prepareStatement(Query_Bussiness);
					statB.setInt(1,this.getBusiness().getChairs());
					statB.setString(2,this.getBusiness().getRoomType());
					statB.setString(3,this.getBusiness().getProjector());
					statB.setString(4,this.getBusiness().getWifi());
					statB.setString(5,this.getHallID());
					statB.setInt(6,hallownerID);
					
					statB.execute();
				}
				
				
				
				
				
			}else {
				if(choice==1) {
					final String Query_value ="INSERT INTO HALL.HALLS(HALL_ID,HALL_NAME,ADDRESS,ROOMS,DAYS,AC_NONAC,PRICE_PER_DAY,INTEREST,EVENT,HALLOWNER_ID)VALUES(?,?,?,?,?,'NON-AC',?,?,?,?)";
					PreparedStatement stat = con.prepareStatement(Query_value);
					stat.setString(1, this.getHallID());
					stat.setString(2, this.getHallName());
					stat.setString(3, this.getAddress());
					stat.setInt(4, this.getRoom());
					stat.setInt(5, this.getDays());
					stat.setInt(6, this.getPrice());
					stat.setInt(7, this.getInterest());
					stat.setString(8, this.getEvents());
					stat.setInt(9, hallownerID);
					
					stat.execute();
				}else {
					final String Query_value ="INSERT INTO HALL.HALLS(HALL_ID,HALL_NAME,ADDRESS,ROOMS,DAYS,AC_NONAC,PRICE_PER_DAY,INTEREST,EVENT,HALLOWNER_ID)VALUES(?,?,?,?,?,'AC',?,?,?,?)";
					PreparedStatement stat = con.prepareStatement(Query_value);
					stat.setString(1, this.getHallID());
					stat.setString(2, this.getHallName());
					stat.setString(3, this.getAddress());
					stat.setInt(4, this.getRoom());
					stat.setInt(5, this.getDays());
					stat.setInt(6, this.getPrice());
					stat.setInt(7, this.getInterest());
					stat.setString(8, this.getEvents());
					stat.setInt(9, hallownerID);
					
					stat.execute();
					
					final String Query_Bussiness="INSERT INTO HALL.BUSINESSEVENT VALUES(?,?,?,?,?,?)";
					PreparedStatement statB=con.prepareStatement(Query_Bussiness);
					statB.setInt(1,this.getBusiness().getChairs());
					statB.setString(2,this.getBusiness().getRoomType());
					statB.setString(3,this.getBusiness().getProjector());
					statB.setString(4,this.getBusiness().getWifi());
					statB.setString(5,this.getHallID());
					statB.setInt(6,hallownerID);
					
					statB.execute();
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		
			
		
	}
	
	public static Boolean isValidID(String hallID) throws SQLException {
		
		boolean valid=false;
		
		Connection con=ConnectionDb.Connect();
		
		//step 3.Creating a Statement
		Statement stat = con.createStatement();
		
		ResultSet result = stat.executeQuery("SELECT HALL_ID FROM HALL.HALLS");
		while(result.next()) {
			if(result.getString(1).equals(hallID)) {
				valid=true;
				break;
			}
			else {
				valid=false;
			}
		}
		
		if(valid) {
			return true;
		}else {
			return false;
		}
		
	}
	
	public static void viewUserHall(String urName,String pass) throws SQLException{
		
		final String query_select = "SELECT HALL_ID,HALL_NAME,ADDRESS,ROOMS,DAYS,AC_NONAC,PRICE_PER_DAY,INTEREST,EVENT FROM HALL.HALLS WHERE HALLOWNER_ID=(SELECT HALLOWNER_ID FROM HALL.HALLOWNERS WHERE USER_NAME=? AND PASSWORD=?)";
		
		Connection con=ConnectionDb.Connect();
		
		PreparedStatement stat = con.prepareStatement(query_select);
		stat.setString(1, urName);
		stat.setString(2, pass);
		
		ResultSet result = stat.executeQuery();
		System.out.println("      +--------------------------------------------------------------------------------------------------------------------------------------------------------------+");
		System.out.println("      |    "+Colors.YELLOW+"HALL_ID"+Colors.RESET+"    |    "+Colors.YELLOW+"HALL_NAME"+Colors.RESET+"      |   "+Colors.YELLOW+"ROOMS"+Colors.RESET+"   |   "+Colors.YELLOW+"DAYS"+Colors.RESET+"   |   "+Colors.YELLOW+"AC_NONAC"+Colors.RESET+"    |    "+Colors.YELLOW+"PRICE_PER_DAY"+Colors.RESET+"    |    "+Colors.YELLOW+"INTEREST"+Colors.RESET+"   |    "+Colors.YELLOW+"EVENT"+Colors.RESET+"              |    "+Colors.YELLOW+"ADDRESS"+Colors.RESET+"          |");
		System.out.println("      +--------------------------------------------------------------------------------------------------------------------------------------------------------------+");
		while(result.next()) {
		    System.out.printf("      |    %-10s |  %-14s   |   %-7s |   %-6s |   %-11s |    %-16s |  %-12s |    %-15s    |    %-15s  |\n",
		        result.getString(1),
		        result.getString(2),
		        result.getString(4),
		        result.getString(5),
		        result.getString(6),
		        result.getString(7),
		        result.getString(8),
		        result.getString(9),
		        result.getString(3)
		    );
		}
		System.out.println("      +--------------------------------------------------------------------------------------------------------------------------------------------------------------+");
		
	}
	
	public static void deleteHall(String hallID) throws SQLException {
		
		final String delete_query="DELETE FROM HALL.HALLS WHERE HALL_ID='?'";
		
			Connection con=ConnectionDb.Connect();
			
			PreparedStatement stat = con.prepareStatement(delete_query);
			stat.setString(1, hallID);
		
	}
	public String getHallID() {
		return hallID;
	}
	public String getHallName() {
		return hallName;
	}
	public String getAddress() {
		return address;
	}
	public int getRoom() {
		return room;
	}
	public int getDays() {
		return days;
	}
	public char getAc() {
		return ac;
	}
	public int getPrice() {
		return price;
	}
	public int getInterest() {
		return interest;
	}
	public String getEvents() {
		return Events;
	}
	public void setHallID(String hallID) {
		this.hallID = hallID;
	}
	public void setHallName(String hallName) {
		this.hallName = hallName;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setRoom(int room) {
		this.room = room;
	}
	public void setDays(int days) {
		this.days = days;
	}
	public void setAc(char ac) {
		this.ac = ac;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public void setInterest(int interest) {
		this.interest = interest;
	}
	public void setEvents(String events) {
		Events = events;
	}

	public BusinessEvent getBusiness() {
		return business;
	}

	public void setBusiness(BusinessEvent business) {
		this.business = business;
	}
	
	
}
//HALL_ID,HALL_NAME,ADDRESS,ROOMS,DAYS,AC_NONAC,PRICE_PER_DAY,INTEREST,EVENT