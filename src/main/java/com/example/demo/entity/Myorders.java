package com.example.demo.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity@Getter@Setter@ToString@NoArgsConstructor@AllArgsConstructor

public class Myorders {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	String username;
	@OneToOne
	Item item;
	int qty;
	Date orderplacedon;
	Date deliveredon;
	long netpriceoforder;
}
