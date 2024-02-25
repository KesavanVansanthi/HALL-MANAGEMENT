package com.userdetails;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.DriverPackage.Colors;
import com.Exceptions.InputInvalidException;
import com.database.*;
import com.userdetaildatabase.CustomerDetailsDatabase;

public class CustomerDetails implements UserDetails {
	CustomerDetailsDatabase cusdata = new CustomerDetailsDatabase();
	private String userName;
	private String Password;
	
	public CustomerDetails(){
		
	}
	
	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return Password;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public CustomerDetails(String userName,String Password) throws IOException {
		this.setUserName(userName);
		this.setPassword(Password);
		this.showDetails();
		
	}

	@Override
	public void showDetails() {
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		System.out.println(Colors.LAVENDER+"\n            --- PROFILE --- "+Colors.RESET);
		System.out.println("\n"+
							"                     _____________  \n"+
							"                    |             | \n"+
							"                    |     ***     | \n"+
							"                    |    *   *    | \n"+
							"                    |     ***     | \n"+
							"                    |    *   *    | \n"+
							"                    |    *****    | \n"+
							"                     -------------  \n");
		System.out.println("          -----------------------------------------");
		
		try {
			cusdata.view(this.getUserName(),this.getPassword());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		System.out.print(Colors.YELLOW+"\n          Do you want to Update ?(y/n) : "+Colors.RESET);
		try {
			char val=sc.readLine().charAt(0);
			if(val=='y') {
				update();
			}
			else if(val=='n') {
				
			}
			else {
				throw new InputInvalidException(Colors.RED+"          --- Invalid Choice ---"+Colors.RESET);
			}
		}catch(Exception e) {
			System.out.println(e);
		}
		
		
		
	}

	@Override
	public void update() {
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		char value='y';
		do {
			try{
//				showDetails();
				System.out.println(Colors.YELLOW+"          What are you trying to Update ?"+Colors.RESET);
				System.out.println("\n           1-User Name    2-First Name     3-Last Name \n"+
									"           4-Age          5-Date of Birth  6-City \n"+
									"           7-Phone Number  8-Email          9-Password ");
				int field = Integer.parseInt(sc.readLine());
				char val='y';
				switch(field) {
				case 1:
					do {
						System.out.println("\n            -----------------------------------------------");
						System.out.print(Colors.YELLOW+"                       New User Name : "+Colors.RESET);
						String newName = sc.readLine();
						System.out.println("            -----------------------------------------------");
						Pattern userNamePattern = Pattern.compile("^[A-Z][a-zA-Z0-9_]{3,16}$");
						Matcher userNameMatcher = userNamePattern.matcher(newName);
						if(!userNameMatcher.matches()){
							val='y';
							try {
						        throw new InputInvalidException(Colors.RESET+"\n          Invalid UserName!!!"+Colors.RESET); 
							}catch(Exception e) {
								System.out.println(e.getMessage());
							}
					     }
						else {
							val='n';
							cusdata.dataUpdate(this.getUserName(),this.getPassword(),1,newName);
						}
						System.out.print(Colors.YELLOW+"\n          Do you want to Update AGAIN ?(y/n) : ");
						try {
							value=sc.readLine().charAt(0);
						}catch(Exception e) {
							System.out.println(e);
						}
					}while(val=='y');
					
					break;
				case 2:
					do {
						System.out.println("\n            -----------------------------------------------");
						System.out.print(Colors.YELLOW+"                      New First Name : "+Colors.RESET);
						String firstName = sc.readLine();
						System.out.println("            -----------------------------------------------");
						if(!Pattern.matches("^[a-zA-Z]+$",firstName)) {
							val='y';
							try {
								throw new InputInvalidException(Colors.RED+"\n          --- Invalid First Name ---"+Colors.RESET);
							}catch(Exception e) {
								System.out.println(e.getMessage());
							}
						}
						else {
							val='n';
							cusdata.dataUpdate(this.getUserName(),this.getPassword(),2,firstName);
							System.out.print(Colors.YELLOW+"\n          Do you want to Update AGAIN ?(y/n) : "+Colors.RESET);
							try {
								value=sc.readLine().charAt(0);
							}catch(Exception e) {
								System.out.println(e);
							}
						}
						
					}while(val=='y');
					
					break;
				case 3:
					do {
						System.out.println("\n            -----------------------------------------------");
						System.out.print(Colors.YELLOW+"                      New Last Name : "+Colors.RESET);
						String lastName = sc.readLine();
						System.out.println("            -----------------------------------------------");
						if(!Pattern.matches("^[a-zA-Z]+$",lastName)) {
							val='y';
							try {
								throw new InputInvalidException(Colors.RED+"\n          --- Invalid Last Name ---"+Colors.RESET);
							}catch(Exception e) {
								System.out.println(e.getMessage());
							}
						}else {
							val='n';
							cusdata.dataUpdate(this.getUserName(),this.getPassword(),3,lastName);
							System.out.print(Colors.YELLOW+"\n          Do you want to Update AGAIN ?(y/n) : "+Colors.RESET);
							try {
								value=sc.readLine().charAt(0);
							}catch(Exception e) {
								System.out.println(e);
							}
						}
						
					}while(val=='y');
					
					break;
				case 4:
					do {
						System.out.println("\n            -----------------------------------------------");
						System.out.print(Colors.YELLOW+"                      New Age : "+Colors.RESET);
						
						try {
							String Age= sc.readLine();
							 System.out.println("            -----------------------------------------------");
							 val='n';
								cusdata.dataUpdate(this.getUserName(),this.getPassword(),4,Age);
								System.out.print(Colors.YELLOW+"\n          Do you want to Update AGAIN ?(y/n) : "+Colors.RESET);
								try {
									value=sc.readLine().charAt(0);
								}catch(Exception e) {
									System.out.println(e);
								}
						}catch(Exception e) {
							val='y';
							System.out.println(Colors.RED+"\n          --- Age Should be a Number"+Colors.RESET);
						}
						
						
					}while(val=='y');
					
					break;
				case 5:
					do {
						System.out.println("\n            -----------------------------------------------");
						System.out.print(Colors.YELLOW+"                    New Data-of-Birth(yyyy-mm-dd) : "+Colors.RESET);
						String dob = sc.readLine();
						System.out.println("            -----------------------------------------------");
						String localDateOfBirth=LocalDate.parse(dob, DateTimeFormatter.ofPattern("yyyy-MM-dd")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//						System.out.println(dateOfBirth);
						if(!Pattern.matches("^\\d{4}-\\d{2}-\\d{2}$",dob)) {
							val='y';
							try {
								throw new InputInvalidException("\n          --- Invaid Date format ---");
							}catch(Exception e) {
								System.out.println(e.getMessage());
							}
						}else {
							val='n';
						}
						cusdata.dataUpdate(this.getUserName(),this.getPassword(),5,dob);
						System.out.print(Colors.YELLOW+"\n          Do you want to Update AGAIN ?(y/n) : "+Colors.RESET);
						try {
							value=sc.readLine().charAt(0);
						}catch(Exception e) {
							System.out.println(e);
						}
					}while(val=='y');
					
					break;
				case 6:
					do {
						System.out.println("\n            -----------------------------------------------");
						System.out.print(Colors.YELLOW+"                     New City : "+Colors.RESET);
						String city = sc.readLine();
						System.out.println("            -----------------------------------------------");
						if(!Pattern.matches("^[a-zA-Z]+$",city)) {
							val='y';
							try {
								throw new InputInvalidException(Colors.RED+"\n          --- Invalid City ---"+Colors.RESET);
							}catch(Exception e) {
								System.out.println(e.getMessage());
							}
						}else {
							val='n';
							cusdata.dataUpdate(this.getUserName(),this.getPassword(),6,city);
							System.out.print(Colors.YELLOW+"\n          Do you want to Update AGAIN ?(y/n) : "+Colors.RESET);
							try {
								value=sc.readLine().charAt(0);
							}catch(Exception e) {
								System.out.println(e);
							}
						}
						
					}while(val=='y');
					
					break;
				case 7:
					do {
						System.out.println("\n            -----------------------------------------------");
						System.out.print(Colors.YELLOW+"                    New Phone Number : "+Colors.RESET);
						String phoneNo = sc.readLine();
						System.out.println("            -----------------------------------------------");
						if(!Pattern.matches("^[6-9][0-9]{9}$",phoneNo )) {
							val='y';
							try {
								throw new InputInvalidException(Colors.RED+"\n          --- Invalid Phone No ---"+Colors.RESET);
							}catch(Exception e) {
								System.out.println(e.getMessage());
							}
						}else {
							val='n';
							cusdata.dataUpdate(this.getUserName(),this.getPassword(),7,phoneNo);
							System.out.print(Colors.YELLOW+"\n          Do you want to Update AGAIN ?(y/n) : "+Colors.RESET);
							try {
								value=sc.readLine().charAt(0);
							}catch(Exception e) {
								System.out.println(e);
							}
						}
						
					}while(val=='y');
					
					break;
				case 8:
					do {
						System.out.println("\n            -----------------------------------------------");
						System.out.print(Colors.YELLOW+"                      New Mail-Id : "+Colors.RESET);
						String mail = sc.readLine();
						System.out.println("            -----------------------------------------------");
						Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$");
						Matcher emailMatcher = emailPattern.matcher(mail);
						if(!emailMatcher.matches()){
							val='y';
					           throw new InputInvalidException(Colors.RED+"\n          --- Invalid Mail ---"+Colors.RESET);   
					     }
						else {
							val='n';
							cusdata.dataUpdate(this.getUserName(),this.getPassword(),8,mail);
						}
						System.out.print(Colors.YELLOW+"\n          Do you want to Update AGAIN ?(y/n) : "+Colors.RESET);
						try {
							value=sc.readLine().charAt(0);
						}catch(Exception e) {
							System.out.println(e);
						}
					}while(val=='y');
					
					break;
				case 9:
					do {
						System.out.println("\n            -----------------------------------------------");
						System.out.print(Colors.YELLOW+"                  Enter Old Password : "+Colors.RESET);
						String oldPass = sc.readLine();
						System.out.println("            -----------------------------------------------");
						UserAdminUpdate uu = new UserAdminUpdate();
						boolean valid = uu.OldPass(this.getUserName(),this.getPassword() , oldPass);
						if(valid) {
							do {
								System.out.println("\n            -----------------------------------------------");
								System.out.print(Colors.YELLOW+"                    New Password : "+Colors.RESET);
								String newPass = sc.readLine();
								System.out.println("            -----------------------------------------------");
								Pattern passwordPattern = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$");
								Matcher passwordMatcher = passwordPattern.matcher(newPass);
								if(!passwordMatcher.matches()){
									val='y';
									try {
							           throw new InputInvalidException(Colors.RED+"\n          --- Invalid Password Format ---"+Colors.RESET);
									}catch(Exception e) {
										System.out.println(e.getMessage());
									}
							     }
								else {
									do {
										System.out.print(Colors.YELLOW+"          Confirm Password : "+Colors.RESET);
										String confirmpass=sc.readLine();
										if(!confirmpass.equals(this.getPassword())) {
											val='y';
											try {
												throw new InputInvalidException(Colors.RED+"          --- Both Password shuld be same ---"+Colors.RESET);
											}catch(Exception e) {
												System.out.println(e.getMessage());
											}
										}else {
											cusdata.dataUpdate(this.getUserName(),this.getPassword(),9,newPass);
											System.out.print(Colors.YELLOW+"\n          Do you want to Update AGAIN ?(y/n) : "+Colors.RESET);
											try {
												value=sc.readLine().charAt(0);
											}catch(Exception e) {
												System.out.println(e);
											}
											val='n';
										}
									}while(val=='y');
									
								}
							}while(val=='y');
							
							
						}
						else {
							val='y';
							try {
								throw new InputInvalidException(Colors.RED+"\n          --- No...No..! Enter valid password --- "+Colors.RESET);
							}catch(Exception e) {
								System.out.println(e.getMessage());
							}
						}
						
					}while(val=='y');
					
					break;
				}
				
			}catch(Exception e) {
				System.out.println(Colors.RED+"\n          ---Invalid Input to Update ---"+Colors.RESET);
				System.out.print(Colors.YELLOW+"\n          Want to try Again ?(y/n) : "+Colors.RESET);
				try{
					value=sc.readLine().charAt(0);
				}catch(Exception E) {
					System.out.println();
				}
			}
		}while(value=='y');
		System.out.println("\n            *******************************");
		System.out.println("            *   --- "+Colors.GREEN+"UPDATED PROFILE"+Colors.RESET+" ---   *");
		System.out.println("            *******************************");
		showDetails();
		
	}

}
