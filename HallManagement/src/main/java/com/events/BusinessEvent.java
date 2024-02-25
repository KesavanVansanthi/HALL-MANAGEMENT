package com.events;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.DriverPackage.Colors;
import com.EventDatabase.BusinessData;
import com.Exceptions.InputInvalidException;

public class BusinessEvent {
	private int chairs;
	private String roomType;
	private String Projector;
	private String wifi;
	
	static BusinessEvent business;
	
	public BusinessEvent(){
		
	}
	
	public BusinessEvent addEvent() throws InputInvalidException, NumberFormatException {
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		
		
		System.out.println("          -----------------------------------------------------");
		System.out.println(Colors.GREEN+"          -: Enter the Details to add the Business Features :- "+Colors.RESET);
		System.out.println("          -----------------------------------------------------");
		
		try {
			System.out.print(Colors.YELLOW+"          Will You Provide Chairs (y/n) : "+Colors.RESET);
			char ch=sc.readLine().charAt(0);
			if(ch=='y')
			{
				System.out.print(Colors.YELLOW+"          Enter the Count of Chairs you will Provide :"+Colors.RESET);
				chairs=Integer.parseInt(sc.readLine());
				char val='y';
				do {
					System.out.println("          ************************");
					System.out.println("          * "+Colors.GREEN+" ### Room Types ### "+Colors.RESET+" *");
					System.out.println("          ************************");
					System.out.println("\n               1.Premium\n               2.Gold\n               3.Silver");
					System.out.println("\n          Premium "+Colors.LAVENDER+"->"+Colors.RESET+Colors.YELLOW+" Sofas, Free Serivces & Unlimited Foods "+Colors.RESET);
					System.out.println("             Gold "+Colors.LAVENDER+"->"+Colors.RESET+Colors.YELLOW+" Chairs with clothes,Cushion & Free Services "+Colors.RESET);
					System.out.println("           Silver "+Colors.LAVENDER+"->"+Colors.RESET+Colors.YELLOW+" Normal Chairs with Cushion "+Colors.RESET);
					System.out.print(Colors.YELLOW+"          Choose Your Room Type : "+Colors.RESET);
					int choice=Integer.parseInt(sc.readLine());
					switch(choice) {
					case 1:
						val='n';
						roomType="PREMIUM";
						break;
					case 2:
						val='n';
						roomType="GOLD";
						break;
					case 3:
						val='n';
						roomType="SILVER";
						break;
					default :
						val='y';
						throw new InputInvalidException(Colors.RED+"          --- Invalid Choice ---"+Colors.RESET);
					}
				}while(val=='y');
				
			}else {
				chairs=0;
				System.out.println(Colors.GREEN+"          --- Your Room Type is Allocated as 'SILVER' ---"+Colors.RESET);
				roomType="SILVER";
			}
			System.out.println(Colors.YELLOW+"          Will Your rooms have Projector ? (y/n) :"+Colors.RESET);
			char choiceprojector=sc.readLine().charAt(0);
			if(choiceprojector=='y') {
				Projector="AVAILABLE";
			}else{
				Projector="NOT AVAILABLE";
			}
			System.out.println(Colors.YELLOW+"          Will your Hall Have any Wifi-Service ? (y/n) :"+Colors.RESET);
			char choicewifi=sc.readLine().charAt(0);
			if(choicewifi=='y') {
				wifi="AVAILABLE";
			}else {
				wifi="NOT AVAILABLE";
			}
			
			business = new BusinessEvent(this.getChairs(),this.getRoomType(),this.getProjector(),this.getWifi());
			
		
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		return business;
	}
	
	public BusinessEvent(String userName,String password,String category) {
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("\n          Our Bussiness Hall Services Could be Useful for Conducting :-");
		System.out.println("\n          ************************************************************");
		System.out.println("          *                 --- "+Colors.GREEN+"Bussiness Meets"+Colors.RESET+" ---                  *");
		System.out.println("          *                  --- "+Colors.GREEN+"Team Hang Ups"+Colors.RESET+" ---                   *");
		System.out.println("          *                   --- "+Colors.GREEN+"Conferences"+Colors.RESET+" ---                    *");
		System.out.println("          ************************************************************");

		System.out.print(Colors.YELLOW+"\n          Do you want to see Available Services ?(y/n) : "+Colors.RESET);
		try {
			char choice = sc.readLine().charAt(0);
			if(choice=='y') {
				BusinessData.viewDetails(userName,password,category);
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public BusinessEvent(int chairs, String roomType, String projector, String wifi) {
		this.setChairs(chairs);
		this.setProjector(projector);
		this.setRoomType(roomType);
		this.setWifi(wifi);
	}

	public int getChairs() {
		return chairs;
	}

	public String getRoomType() {
		return roomType;
	}

	public String getProjector() {
		return Projector;
	}

	public String getWifi() {
		return wifi;
	}

	public void setChairs(int chairs) {
		this.chairs = chairs;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public void setProjector(String projector) {
		Projector = projector;
	}

	public void setWifi(String wifi) {
		this.wifi = wifi;
	}


}
