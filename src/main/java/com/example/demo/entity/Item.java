package com.example.demo.entity;

import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import com.example.demo.sellerEntity.File;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity@Getter@Setter@ToString@NoArgsConstructor@AllArgsConstructor

public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	String itemname;
	String subcategory;
	String category;//electronic, hardwere,grocery,detergents,dry fruits,vegetables,etc
	float price;
	int purchasePrice;
	byte regulardisc;
	byte primedisc;
	Date dateOfupload;
	byte qty;
	String brand;
	String allitemdetails;
    float rating;
	int numberofdelivariesdone;
	@OneToMany
	List<Reviews> reviews;
	@OneToOne
	File file;
	
	
	

}
