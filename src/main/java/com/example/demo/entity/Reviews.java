package com.example.demo.entity;

import java.sql.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.example.demo.sellerEntity.File;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Entity@Getter@Setter@ToString@NoArgsConstructor@AllArgsConstructor

public class Reviews {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	String userid;
	float star;
	String comment;
	Date reviewdate;
	@OneToOne
	File productimg;
	
}
