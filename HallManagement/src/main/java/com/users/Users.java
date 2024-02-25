package com.users;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.SQLException;

import com.userdetails.*;
import com.Booking.Book;
import com.DriverPackage.Colors;
import com.EventDatabase.BookHall;
import com.Exceptions.InputInvalidException;
import com.enums.CATEGORY;

public class Users {
	
	private String userID;
	private String firstName;
	private String lastName;
	private int age;
	private String dateOfBirth;
	private String city;
	private String phoneNo;
	private String mail;
	private String password;
	private String category;
	
	public Users() {
		
	}
	
//	public Users(String category) {
//		this.setCategory(category);
//	}
//	
	
	public Users(String userID,String password,String category) throws SQLException {
		this.setUserID(userID);
		this.setPassword(password);
		this.setCategory(category);
//		Users user = new Users(category);
//		new BookHall(user);
		this.Service();
	}
	
	public Users(String userID,String firstName,String lastName,int age,String dateOfBirth,String city,String phoneNo,String mail,String password){
		this.setUserID(userID);
		this.setAge(age);
		this.setCity(city);
		this.setDateOfBirth(dateOfBirth);
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setMail(mail);
		this.setPhoneNo(phoneNo);
		this.setPassword(password);
		
	}
	
	public String getUserID() {
		return userID;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category=category;
	}

	public String getPassword() {
		return password;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void Service(){
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		char ch='y';
		do {
			try {
				if("ADMIN".equalsIgnoreCase(this.getCategory()))
				{
					System.out.println("\n          ***************************************************************");
					System.out.println("          *         "+Colors.GREEN+"   --- SERVICE OPTIONS for "+CATEGORY.ADMIN+" ---      "+Colors.RESET+"      *");
					System.out.println("          ***************************************************************");
					System.out.println("          *                     1.View Profile                          *");
					System.out.println("          *                     2.View Halls                            *");   //any-else?
					System.out.println("          *                     3.validate Booking Requests             *");
					System.out.println("          *                     4.View History                          *"+"\n"
							         + "          *                     5.Cancel and Refund                     *"+"\n"
									+  "          *                     6.Back to Menu                          *");
					System.out.println("          ***************************************************************");
					System.out.print(Colors.YELLOW+"\n          Enter Your Choice : "+Colors.RESET);
					int choice = Integer.parseInt(sc.readLine());
					switch(choice){
					case 1:
						AdminDetails admindetails = new AdminDetails(this.getUserID(),this.getPassword());
						break;
					case 2:
						Admin.viewHall(this.getUserID(),this.getPassword(),this.getCategory());
						break;
					case 3:
						Admin.verifyBooking(this.getUserID(),this.getPassword());
						break;
					case 4:
						//bookings
						Book book = new Book();
						book.viewBook(this.getUserID(),this.getPassword(),this.getCategory());
						break;
					case 5:
						//refund
						BookHall.refund(userID, password, category);
						break;
					case 6:
						ch='n';
						break;
					default:
						throw new InputInvalidException("          --- OOP's Invalid Choice ---");		
					}
					System.out.println();
				}
				else if("HAllOWNER".equalsIgnoreCase(this.getCategory())) {
					System.out.println("\n          ***************************************************************");
					System.out.println("          *         "+Colors.GREEN+"   --- SERVICE OPTIONS for "+CATEGORY.HALLOWNER+" ---      "+Colors.RESET+"      *");
					System.out.println("          ***************************************************************");
					System.out.println("          *                       1.View Profile                        *");
					System.out.println("          *                       2.View Halls                          *");
					System.out.println("          *                       3.Add Halls                           *");
					System.out.println("          *                       4.Update Halls                        *");
					System.out.println("          *                       5.View History                        *"+"\n"
							         + "          *                       6.Cancel and Refund                   *");
					System.out.println("          *                       7.Back to Menu                        *");
					System.out.println("          ***************************************************************");
					System.out.print(Colors.YELLOW+"\n          Enter Your Choice : "+Colors.RESET);
					int choice = Integer.parseInt(sc.readLine());
					switch(choice){
					case 1:
						new HallOwnerDetails(this.getUserID(),this.getPassword());
						break;
					case 2:
						HallOwner.viewHallService(this.getUserID(),this.getPassword(),this.getCategory());
						break;
					case 3:
						HallOwner owner = new HallOwner();
						owner.addHall(this.getUserID(),this.getPassword());
						break;
					case 4:
						HallOwner owner1 = new HallOwner();
						owner1.viewHall(this.getUserID(),this.getPassword());
						break;
					case 5:
						//bookings
						do {
							System.out.println(Colors.GREEN+"          Options :-"+Colors.RESET);
							System.out.println("             1.View My Halls");
							System.out.println("             2.View Booked Requests");
							System.out.print(Colors.YELLOW+"\n          Enter Your Preference : "+Colors.RESET);
							int option=Integer.parseInt(sc.readLine());
							switch(option) {
							case 1:
								ch='n';
								Book.viewMyHall(this.getUserID(),this.getPassword());
								break;
							case 2:
								ch='n';
								Book book = new Book();
								book.viewBook(this.getUserID(),this.getPassword(),this.getCategory());
								break;
							default :
								ch='y';
								throw new InputInvalidException(Colors.RED+"          --- Invalid Option ---"+Colors.RESET);
							}
						}while(ch=='y');
						break;
					case 6:
						//Refund
						BookHall.refund(userID, password, category);
						break;
					case 7:
						ch='n';
						break;
					default:
						throw new InputInvalidException("          --- OOP's Invalid Choice ---");		
					}
					System.out.println();
				}
				else if("CUSTOMER".equalsIgnoreCase(this.getCategory())) {
					System.out.println("\n          ***************************************************************");
					System.out.println("          *         "+Colors.GREEN+"   --- SERVICE OPTIONS for "+CATEGORY.CUSTOMER+" ---      "+Colors.RESET+"      *");
					System.out.println("          ***************************************************************");
					System.out.println("          *                      1.View Profile                         *");
					System.out.println("          *                      2.View Halls                           *");
					System.out.println("          *                      3.View History                         *"+"\n"
							         + "          *                      4.Cancel and Refund                    *");
					System.out.println("          *                      5.Back to Menu                         *");
					System.out.println("          ***************************************************************");
					System.out.print(Colors.YELLOW+"\n          Enter Your Choice : "+Colors.RESET);
					int choice = Integer.parseInt(sc.readLine());
					switch(choice){
					case 1:
						new CustomerDetails(this.getUserID(),this.getPassword());
						break;
					case 2:
						Customers.viewHallService(this.getUserID(),this.getPassword(),this.getCategory());
						break;
					case 3:
						Book book = new Book();
						book.viewBook(this.getUserID(),this.getPassword(),this.getCategory());
						break;
					case 4:
						BookHall.refund(userID, password, category);
						break;
					case 5:
						ch='n';
						break;
					default:
						throw new InputInvalidException(Colors.RED+"          --- OOP's Invalid Choice ---"+Colors.RESET);		
					}
					System.out.println();
				}
				else {
					throw new InputInvalidException(Colors.RED+"          --- Unspecified Category ---"+Colors.RESET);
				}
				
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}while(ch=='y');
		
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public int getAge() {
		return age;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public String getCity() {
		return city;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public String getMail() {
		return mail;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
	
	

}
