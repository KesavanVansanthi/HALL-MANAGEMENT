package com.database;

import java.sql.*;

import com.databaseConnection.ConnectionDb;
import com.users.Users;

public class RegisterDatabase {
	private Users users;
	
	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public void insertDetails(Users users,char val){
		this.setUsers(users);
		Connection con = ConnectionDb.Connect();
		final String Query_insertOwner = "INSERT INTO HAll.HALLOWNERS (USER_NAME,PASSWORD) VALUES(?,?)";
		final String Query_insertUser = "INSERT INTO HAll.USERS (USER_NAME,FIRST_NAME,LAST_NAME,AGE,DATE_OF_BIRTH,CITY,PHONE_NO,EMAIL,PASSWORD,CATEGORY) VALUES(?,?,?,?,?,?,?,?,?,?)";
		
		PreparedStatement stat;
		try {
			stat = con.prepareStatement(Query_insertUser);
			PreparedStatement statOwner = con.prepareStatement(Query_insertOwner);
			
			if(val=='y') {
				stat.setString(1, users.getUserID());
				stat.setString(2, users.getPassword());
				statOwner.setString(1, users.getUserID());
				statOwner.setString(2, users.getFirstName());
				statOwner.setString(3, users.getLastName());
				statOwner.setInt(4, users.getAge());
				statOwner.setString(5, users.getDateOfBirth());
				statOwner.setString(6, users.getCity());
				statOwner.setString(7, users.getPhoneNo());
				statOwner.setString(8, users.getMail());
				statOwner.setString(9, users.getPassword());
				statOwner.setString(10, users.getCategory());
			}else {
				statOwner.setString(1, users.getUserID());
				statOwner.setString(2, users.getFirstName());
				statOwner.setString(3, users.getLastName());
				statOwner.setInt(4, users.getAge());
				statOwner.setString(5, users.getDateOfBirth());
				statOwner.setString(6, users.getCity());
				statOwner.setString(7, users.getPhoneNo());
				statOwner.setString(8, users.getMail());
				statOwner.setString(9, users.getPassword());
				statOwner.setString(10, users.getCategory());
				
			}
				
			stat.execute();
			statOwner.execute();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		
//		return ;
	}
	
	public Boolean userValid(String userName){
		
		boolean valid=false;
		
		Connection con = ConnectionDb.Connect();
		Statement stat;
		try {
			stat = con.createStatement();
			ResultSet result = stat.executeQuery("SELECT USER_NAME FROM HALL.USERS");
			
			while(result.next()) {
				if(result.getString(1).equals(userName)) {
					valid=true;
					break;
				}
				else {
					valid=false;
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
			
		if(valid) {
			return true;
		}else {
			return false;
		}
		
	}
}
	


//	USER_NAME,FIRST_NAME,LAST_NAME,AGE,DATE_OF_BIRTH,CITY,PHONE_NO,EMAIL
