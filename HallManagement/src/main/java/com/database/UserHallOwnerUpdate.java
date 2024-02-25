package com.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.Exceptions.InputInvalidException;
import com.databaseConnection.ConnectionDb;

public class UserHallOwnerUpdate {
	
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

	
	public UserHallOwnerUpdate() {
		
	}
	
	public UserHallOwnerUpdate(String username,String pass,int field,String value){
		
		this.setUsername(username);
		this.setPass(pass);
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
		
		final String Update_Value1 = "UPDATE HALL.HAllOWNERS SET USER_NAME=? WHERE USER_NAME=? AND PASSWORD=?";
		final String Update_Value = "UPDATE HALL.USERS SET USER_NAME =? WHERE USER_NAME=? AND PASSWORD=?";
		
		try {
			Connection con=ConnectionDb.Connect();
			
			//step 3.Creating a Statement
			PreparedStatement stat = con.prepareStatement(Update_Value);
			PreparedStatement stat1 = con.prepareStatement(Update_Value);
			
			stat.setString(1,this.getValue());
			stat.setString(2,this.getUsername());
			stat.setString(3,this.getPass());
			
			
			stat1.setString(1,this.getValue());
			stat1.setString(2,this.getUsername());
			stat1.setString(3,this.getPass());
	
			stat.execute();
			stat1.execute();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		
		
	}
	
	public void firstName(){
		
		final String Update_Value ="UPDATE HALL.USERS SET FIRST_NAME=? WHERE USER_NAME=? AND PASSWORD=?";
		
		try {
			Connection con=ConnectionDb.Connect();
			
			//step 3.Creating a Statement
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
			
			//step 3.Creating a Statement
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
			
			//step 3.Creating a Statement
			PreparedStatement stat = con.prepareStatement(Update_Value);
		
			stat.setInt(1,Integer.parseInt(this.getValue()));
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
			
			//step 3.Creating a Statement
			PreparedStatement stat = con.prepareStatement(Update_Value);
		
			stat.setString(1,this.getValue());
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
			
			//step 3.Creating a Statement
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
			
			//step 3.Creating a Statement
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
		
		final String Update_Value ="UPDATE HALL.USERS SET EMAIL =? WHERE USER_NAME=? AND PASSWORD=?";
		
		try {
			Connection con=ConnectionDb.Connect();
			
			//step 3.Creating a Statement
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
			
			//step 3.Creating a Statement
			PreparedStatement stat = con.prepareStatement("SELECT PASSWORD FROM HALL.HAllOWNERS WHERE USER_NAME=? AND PASSWORD=?");
		
			stat.setString(1,this.getUsername());
			stat.setString(2,this.getPass());
				
			ResultSet result = stat.executeQuery();
			
			while(result.next()) {
				if(!oldPass.equals(result.getString(1))) {
					valid=false;
					throw new InputInvalidException("          --- No...No..! Enter valid password --- ");
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
		
		final String Update_Value1 ="UPDATE HALL.HAllOWNERS SET PASSWORD =? WHERE USER_NAME=? AND PASSWORD=?";
		final String Update_Value ="UPDATE HALL.USERS SET PASSWORD =? WHERE USER_NAME=? AND PASSWORD=?";
		
		try {
			Connection con=ConnectionDb.Connect();
			
			//step 3.Creating a Statement
			PreparedStatement stat = con.prepareStatement(Update_Value);
			PreparedStatement stat1 = con.prepareStatement(Update_Value);
		
			stat.setString(1,this.getValue());
			stat.setString(2,this.getUsername());
			stat.setString(3,this.getPass());
			
			stat1.setString(1,this.getValue());
			stat1.setString(2,this.getUsername());
			stat1.setString(3,this.getPass());
		
			stat.execute();
			stat1.execute();
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		
	}
}

