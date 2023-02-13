package com.example.demo.entity;

import java.util.List;

import javax.persistence.ManyToMany;

import com.example.demo.sellerEntity.Profile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter@Setter@ToString@NoArgsConstructor
public class KeyWithStatus {

	int status;
	String tokan;
	
	Profile profile;
	String email;
	String deliveryaddress;
	int pincode;
	long mywallet;
	List<Mycart> mycart;
	List<Myorders> myorders;
	public KeyWithStatus(int status, String tokan, Profile profile) {
		super();
		this.status = status;
		this.tokan = tokan;
		this.profile = profile;
	}
	public KeyWithStatus( String tokan,int status, List<Myorders> myorders,String email,String deliveryaddress,int pincode,long mywallet,List<Mycart> mycart) {
		super();
		this.status = status;
		this.tokan = tokan;
		this.myorders = myorders;
		this.email=email;
		this.deliveryaddress=deliveryaddress;
		this.pincode=pincode;
		this.mywallet=mywallet;
		this.mycart=mycart;
				
	}
	
}
