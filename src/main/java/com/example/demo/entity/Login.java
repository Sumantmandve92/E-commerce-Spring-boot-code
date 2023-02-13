package com.example.demo.entity;




import javax.persistence.Entity;
import javax.persistence.Id;


import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity@Getter@Setter@ToString@NoArgsConstructor@AllArgsConstructor
@Component
public class Login {

	@Id
	String username;
	String password;
	String email;
	String deliveryaddress;
	int pincode;
	long mywallet;
	
	

}
