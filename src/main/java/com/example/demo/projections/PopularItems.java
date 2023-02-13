package com.example.demo.projections;

import com.example.demo.sellerEntity.File;

public interface PopularItems {

	int getId();
	String getItemname();
	String getSubcategory();
	String getCategory();//electronic, hardwere,grocery,detergents,dry fruits,vegetables,etc
	float getPrice();
	
	byte getRegulardisc();
	byte getPrimedisc();
	
	byte getQty();
	String getBrand();

	int getNumberofdelivariesdone();
	float getRatingstar();
	String getAllitemdetails();
	File getFile();
}
