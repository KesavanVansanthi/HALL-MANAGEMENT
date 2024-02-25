package com.database;

import java.sql.*;

import com.databaseConnection.ConnectionDb;
import com.users.Users;

/**
 * 
 * 
 * 
 */

public class UserLogin {
	
	private String user;
	private String pass;
	
	private boolean valid;
	
	public UserLogin() {
		
	}
	
	
	public Boolean validate(String user,String pass,String Category) throws SQLException{
		this.setUser(user);
		this.setPass(pass);
		
		Connection con=ConnectionDb.Connect();
		
		//step 3.Creating a Statement
		Statement stat = con.createStatement();
		
		if(Category.equalsIgnoreCase("ADMIN")) {
			ResultSet result = stat.executeQuery("select USER_NAME,PASSWORD FROM HALL.ADMINS");
			while(result.next()) {
				if(result.getString(1).equals(this.getUser()) && result.getString(2).equals(this.getPass())) {
					valid=true;
				}
			}
			
		}else if(Category.equalsIgnoreCase("HALLOWNER")) {
			ResultSet result = stat.executeQuery("select USER_NAME,PASSWORD FROM HALL.USERS");
			while(result.next()) {
				if(result.getString(1).equals(this.getUser()) && result.getString(2).equals(this.getPass())) {
					valid=true;
				}
			}
			
		}else if(Category.equalsIgnoreCase("Customer")){
			
			ResultSet result = stat.executeQuery("SELECT USER_NAME,PASSWORD FROM HALL.USERS");
			while(result.next()) {
				if(result.getString(1).equals(this.getUser()) && result.getString(2).equals(this.getPass())) {
					valid=true;
				}
			}
		}
		
		if(valid) {
			return true;
		}else
		{
			return false;
		}
		
		
	}
	
	public static Boolean checkUserName(String userName) throws SQLException {
		boolean valid=false;
		Connection con=ConnectionDb.Connect();
		Statement stat = con.createStatement();
		
		ResultSet result = stat.executeQuery("SELECT USER_NAME FROM HALL.USERS");
		
		while(result.next()) {
			if(!result.getString(1).equals(userName)) {
				valid=true;
			}
		}
		
		if(valid) {
			return true;
		}else {
			return false;
		}
	}


	public boolean isValid() {
		return valid;
	}


	public void setValid(boolean valid) {
		this.valid = valid;
	}


	public String getUser() {
		return user;
	}


	public String getPass() {
		return pass;
	}


	public void setUser(String user) {
		this.user = user;
	}


	public void setPass(String pass) {
		this.pass = pass;
	}

}
