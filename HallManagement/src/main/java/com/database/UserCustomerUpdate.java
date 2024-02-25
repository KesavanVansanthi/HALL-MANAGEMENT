package com.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Date;

import com.DriverPackage.Colors;
import com.Exceptions.InputInvalidException;
import com.databaseConnection.ConnectionDb;

public class UserCustomerUpdate {
	
	private String username;
	private String pass;
	private String value;
	
	public String getUsername() {
		return username;
	}

	public String getPass() {
		return pass;
	}

	public String getValue() {
		return value;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public UserCustomerUpdate() {
		
	}
	
	public UserCustomerUpdate(String username,String pass,int field,String value) throws SQLException{
		this.setPass(pass);
		this.setUsername(username);
		this.setValue(value);
		try {
			switch(field) {
			case 1:
				this.changeName();
				break;
			case 2:
				this.firstName();
				break;
			case 3:
				this.lastName();
				break;
			case 4:
				this.age();
				break;
			case 5:
				this.dateOfBirth();
				break;
			case 6:
				this.city();
				break;
			case 7:
				this.phoneNo();
				break;
			case 8:
				this.mail();
				break;
			case 9:
				this.newPass();
				break;
			
			
			}
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		
	}
	
	public void changeName(){
		
		final String Update_Value = "UPDATE HALL.USERS SET USER_NAME =? WHERE USER_NAME=? AND PASSWORD=?";
		
		try {
			Connection con=ConnectionDb.Connect();
			
			PreparedStatement stat = con.prepareStatement(Update_Value);
			
			stat.setString(1,this.getValue());
			stat.setString(2,this.getUsername());
			stat.setString(3,this.getPass());
			
			stat.execute();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		
	}
	
	public void firstName(){
		
		final String Update_Value ="UPDATE HALL.USERS SET FIRST_NAME =? WHERE USER_NAME=? AND PASSWORD=?";
		
		try {
			Connection con=ConnectionDb.Connect();
			
			PreparedStatement stat = con.prepareStatement(Update_Value);
				
			
			stat.setString(1,this.getValue());
			stat.setString(2,this.getUsername());
			stat.setString(3,this.getPass());
			
			stat.execute();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		
		
	}
	
	public void lastName(){
		
		final String Update_Value = "UPDATE HALL.USERS SET LAST_NAME =? WHERE USER_NAME=? AND PASSWORD=?";
		
		try {
			Connection con=ConnectionDb.Connect();
			
			PreparedStatement stat = con.prepareStatement(Update_Value);
			
			stat.setString(1,this.getValue());
			stat.setString(2,this.getUsername());
			stat.setString(3,this.getPass());
			
			stat.execute();
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		
		
	}
	
	public void age(){
		
		final String Update_Value ="UPDATE HALL.USERS SET AGE =? WHERE USER_NAME=? AND PASSWORD=?";
		
		try {
			Connection con=ConnectionDb.Connect();
			
			PreparedStatement stat = con.prepareStatement(Update_Value);
				
			
			stat.setString(1,this.getValue());
			stat.setString(2,this.getUsername());
			stat.setString(3,this.getPass());
			
			stat.execute();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		
		
			
	}
	
	public void dateOfBirth(){
	
		final String Update_Value = "UPDATE HALL.USERS SET DATE_OF_BIRTH =? WHERE USER_NAME=? AND PASSWORD=?";
		
		try {
			Connection con=ConnectionDb.Connect();
			
			PreparedStatement stat = con.prepareStatement(Update_Value);
			
			String dateString = this.getValue(); // Example date string in yyyy-MM-dd format
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilDate = sdf.parse(dateString);
            Date sqlDate = new Date(utilDate.getTime());
				
			stat.setDate(1, sqlDate);
			stat.setString(2,this.getUsername());
			stat.setString(3,this.getPass());
			
			stat.execute();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		
			
	}
	public void city(){
		
		final String Update_Value ="UPDATE HALL.USERS SET CITY =? WHERE USER_NAME=? AND PASSWORD=?";
		
		try {
			Connection con=ConnectionDb.Connect();
			
			PreparedStatement stat = con.prepareStatement(Update_Value);
			
			
			stat.setString(1,this.getValue());
			stat.setString(2,this.getUsername());
			stat.setString(3,this.getPass());
			
			stat.execute();
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		
			
	}
	
	public void phoneNo(){
		
		final String Update_Value ="UPDATE HALL.USERS SET PHONE_NO =? WHERE USER_NAME=? AND PASSWORD=?";
		
		try {
			Connection con=ConnectionDb.Connect();
			
			PreparedStatement stat = con.prepareStatement(Update_Value);
			
			
			stat.setString(1,this.getValue());
			stat.setString(2,this.getUsername());
			stat.setString(3,this.getPass());
			
			stat.execute();
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		
			
	}
	
	public void mail(){
		
		final String Update_Value ="UPDATE HALL.USERS SET EMAIL=? WHERE USER_NAME=? AND PASSWORD=?";
		
		try {
			Connection con=ConnectionDb.Connect();
			
			PreparedStatement stat = con.prepareStatement(Update_Value);
			
			stat.setString(1,this.getValue());
			stat.setString(2,this.getUsername());
			stat.setString(3,this.getPass());
			
			stat.execute();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		
			

	}
	
	
	public boolean OldPass(String username,String pass,String oldPass){
		boolean valid=false;
		
		try {
			Connection con=ConnectionDb.Connect();
			
			PreparedStatement stat = con.prepareStatement("SELECT PASSWORD FROM HALL.USERS WHERE USER_NAME=? AND PASSWORD=?");
				
			ResultSet result = stat.executeQuery();
			
			stat.setString(1,username);
			stat.setString(2,pass);
			
			while(result.next()) {
				if(!oldPass.equals(result.getString(1))) {
					valid=false;
					throw new InputInvalidException(Colors.RED+"          --- No...No..! Enter valid password --- "+Colors.RESET);
				}
				else {
					valid=true;
				}
			}
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		
		
		return valid;
	}
	
	public void newPass(){
		
		final String Update_Value ="UPDATE HALL.USERS SET PASSWORD =? WHERE USER_NAME=? AND PASSWORD=?";
		
		try {
			Connection con=ConnectionDb.Connect();
			
			PreparedStatement stat = con.prepareStatement(Update_Value);
			
			stat.setString(1,this.getValue());
			stat.setString(2,this.getUsername());
			stat.setString(3,this.getPass());
			
			stat.execute();
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		

	}
}
