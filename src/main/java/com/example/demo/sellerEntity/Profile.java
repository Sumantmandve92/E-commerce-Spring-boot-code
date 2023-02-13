package com.example.demo.sellerEntity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity@Getter@Setter@ToString@NoArgsConstructor@AllArgsConstructor

public class Profile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	String fullname;
	String email2;
	String mobileno;
	String aadharno;
	String panno;
	String shopname;
	String shopaddress;
	String homeaddress;
	String bankaccno;
	String branchname;
	String ifscno;
	
	
	
	
}
