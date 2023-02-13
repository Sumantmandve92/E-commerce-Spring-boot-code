package com.example.demo.sellerEntity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.stereotype.Component;

import com.example.demo.entity.Item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity@Getter@Setter@ToString@NoArgsConstructor@AllArgsConstructor
@Component
public class SellerLogin {

	@Id
	String username;
	String password;
	String email;
	@OneToOne
	Profile profile;
	@OneToMany
	List<Item> itemList;
}
