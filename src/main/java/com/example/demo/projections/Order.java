package com.example.demo.projections;

import java.util.Date;

public interface Order {

	int getId();
	Date getOrderdate();
	int getQty();
	String getBrand();
	String getCategory();
	String getItemname();
}
