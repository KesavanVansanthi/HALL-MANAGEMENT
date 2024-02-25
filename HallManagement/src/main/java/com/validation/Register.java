package com.validation;
import java.io.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.database.*;
import com.users.Users;
import com.DriverPackage.Colors;
import com.Exceptions.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register {
	private String userName;
	private String firstName;
	private String lastName;
	private String mail;
	private String phoneNo;
	private int age;
	private String dateOfBirth;
	private String password;
	private String city;
	public String category="ADMIN";

	public String getUserName() {
		return userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getMail() {
		return mail;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public int getAge() {
		return age;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public String getPassword() {
		return password;
	}

	public String getCity() {
		return city;
	}

	public String getCategory() {
		return category;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	
	public void setAge(int age) {
		this.age = age;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setCategory(String category) {
		this.category = category;
	}




	public Register() throws IOException, InputInvalidException {
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		RegisterDatabase register = new RegisterDatabase();
		System.out.println("\n          ***************************************");
		System.out.println("          *     "+Colors.LAVENDER+"    --- REGISTER HERE ---  "+Colors.RESET+"     *");
		System.out.println("          ***************************************");

		char value='y';
		do {
			try {
				System.out.println("\n          --------------------------------------");
				System.out.println(Colors.GREEN+"                ### Enter the Details ### "+Colors.RESET);
				System.out.println("          --------------------------------------\n");
				
				char val='y';
				do {
					System.out.print(Colors.YELLOW+"          Enter User Name : "+Colors.RESET);
					String userName=sc.readLine();
					this.setUserName(userName);
					Pattern userNamePattern = Pattern.compile("^[A-Z][a-zA-Z0-9_]{3,16}$");
					Matcher userNameMatcher = userNamePattern.matcher(this.getUserName());
					if(!userNameMatcher.matches()){
				        throw new InputInvalidException(Colors.RED+"\n          --- Invalid UserName ---"+Colors.RESET);          
				     }else{
				    	 boolean matches = register.userValid(this.getUserName());
				    	 if(matches) {
							val='y';
							throw new InputInvalidException(Colors.RED+"\n          --- User Name Already Exist --- "+Colors.RESET);
						}
						else {
							val='n';
						}
				     }
					
				}while(val=='y');
				
				do {
					System.out.print(Colors.YELLOW+"          You First Name : "+Colors.RESET);
					String firstName=sc.readLine();
					this.setFirstName(firstName);
					if(this.getFirstName() != null && this.getFirstName().matches("-?\\d+(\\.\\d+)?")){
						val='y';
						try {
							throw new InputInvalidException(Colors.RED+"\n          --- FirstName should Not be Numbers ---"+Colors.RESET); 
						}catch(Exception e) {
							System.out.println(e.getMessage());
						}
				     }else {
				    	 val='n';
				     }
				}while(val=='y');
				
				do {
					System.out.print(Colors.YELLOW+"          You Last Name : "+Colors.RESET);
					this.setLastName(sc.readLine());
					if(this.getLastName() != null && this.getLastName().matches("-?\\d+(\\.\\d+)?")){
						val='y';
						try {
							throw new InputInvalidException(Colors.RED+"\n          --- LastName should Not be Numbers ---"+Colors.RESET);  
						}catch(Exception e){
							System.out.println(e.getMessage());
						}
				     }else {
				    	 val='n';
				     }
				}while(val=='y');
				
				do {
					System.out.print(Colors.YELLOW+"          Age : "+Colors.RESET);
					try {
						this.setAge(Integer.parseInt(sc.readLine()));
						val='n';
					}catch(Exception e){
						val='y';
						System.out.println(Colors.RED+"          Age Should not be Letters"+Colors.RESET);
					}
					
				}while(val=='y');
				
				do {
					System.out.print(Colors.YELLOW+"          Phone Number : "+Colors.RESET);
					this.setPhoneNo(sc.readLine());
					Pattern phonePattern = Pattern.compile("^[6-9][0-9]{9}$");
					Matcher phoneMatcher = phonePattern.matcher(this.getPhoneNo());
					if(!phoneMatcher.matches()){
						val='y';
						try {
							throw new InputInvalidException(Colors.RED+"\n          --- Should be Valid Indian Number ---"+Colors.RESET);    
						}catch(Exception e) {
							System.out.println(e.getMessage());
						}
				     }else {
				    	 val='n';
				     }
				}while(val=='y');
				
				do {
					System.out.print(Colors.YELLOW+"          Date.of.Birth(yyyy-MM-dd) : "+Colors.RESET);
					String userdob=sc.readLine();
					Pattern datePattern = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
					Matcher dateMatcher = datePattern.matcher(this.getDateOfBirth());
					
					if(!dateMatcher.matches()){
						val='y';
						try {
							throw new InputInvalidException(Colors.RED+"\n          --- Please Enter the Date as in yyyy-mm-dd Format ---"+Colors.RESET);    
						}catch(Exception e) {
							System.out.println(e.getMessage());
						}
				     }else {
				    	 val='n';
				    	 LocalDate date = LocalDate.parse(userdob, DateTimeFormatter.ISO_DATE);
						 setDateOfBirth(date.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
				     }
				}while(val=='y');
				
				
//				String localDateOfBirth=LocalDate.parse(userdob, DateTimeFormatter.ofPattern("yyyy-MM-dd")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
////				System.out.println(dateOfBirth);
//				String[] dob=localDateOfBirth.split("-");
//				this.setDateOfBirth(dob[2]+"/"+dob[1]+"/"+dob[0]);
				
				 
				do {
					System.out.print(Colors.YELLOW+"          your mail : "+Colors.RESET);
					this.setMail(sc.readLine());
					Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$");
					Matcher emailMatcher = emailPattern.matcher(this.getMail());
					if(!emailMatcher.matches()){
						val='y';
						try {
							throw new InputInvalidException(Colors.RED+"          --- Invalid Mail ---"+Colors.RESET); 
						}catch(Exception e) {
							System.out.println(e.getMessage());
						}
				          
				    }else{
				    	val='n';
				    }
				    
				}while(val=='y');
				
				do {
					System.out.print(Colors.YELLOW+"          City : "+Colors.RESET);
					this.setCity(sc.readLine());
					try {
						if(!Pattern.matches(this.getCity(),"^[a-zA-Z]+$")) {
							val='y';
							throw new InputInvalidException(Colors.RED+"\n          --- City Should not be a Number ---"+Colors.RESET);
						}else {
							val='n';
						}
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
				}while(val=='y');
				
				do {
					System.out.print(Colors.YELLOW+"          Password : "+Colors.RESET);
					this.setPassword(sc.readLine());
					Pattern passwordPattern = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$");
					Matcher passwordMatcher = passwordPattern.matcher(this.getPassword());
					if(!passwordMatcher.matches()){
						val='y';
				        throw new InputInvalidException(Colors.RED+"          --- Invalid Password Please Check Again the Condition ---"+Colors.RESET);
				     }else {
				    	 System.out.print(Colors.YELLOW+"          Confirm Password : "+Colors.RESET);
				    	 String confirmpass=sc.readLine();
				    	 if(!confirmpass.equals(this.getPassword())) {
				    		 val='y';
				    		 try {
				    			 throw new InputInvalidException(Colors.RED+"          --- Both Password shuld be same ---"+Colors.RESET);
				    		 }catch(Exception e) {
				    			 System.out.println(e.getMessage());
				    		 }
						}
				    	 else {
				    		 val='n';
				    	 }
				     }
					
				}while(val=='y');
				char choice='y';
				do {
					System.out.print(Colors.YELLOW+"\n          Do you want to Add Halls ? (y/n) : "+Colors.RESET);
					choice=sc.readLine().charAt(0);
					
					if(choice=='y') {
						val='n';
						this.setCategory("HALLOWNER");
					}else if(choice=='n'){
						val='n';
						this.setCategory("CUSTOMER");
					}else {
						val='n';
						try {
							throw new InputInvalidException(Colors.RED+"\n          --- Invalid Input ---"+Colors.RESET);
						}catch(Exception e){
							System.out.println(e.getMessage());
						}
					}
				}while(val=='y');
				
				
				System.out.println("          You are Categorized into : --- "+Colors.GREEN+category+Colors.RESET+" ---");
				
				
				Users users = new Users(userName,firstName,lastName,age,dateOfBirth,city,phoneNo,mail,password);
				register.insertDetails(users,choice);
				value='n';
				new Login(this.getCategory());
				
			}catch(Exception e) {
				System.out.println(e.getMessage());
				System.out.print(Colors.YELLOW+"\n          Do you want to continue Register ?(y/n) : "+Colors.RESET);
				value=sc.readLine().charAt(0);
				
			}
		}while(value=='y');
//		sc.close();
	}
	
}
