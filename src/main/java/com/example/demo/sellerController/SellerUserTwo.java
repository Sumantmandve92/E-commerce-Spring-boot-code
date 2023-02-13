package com.example.demo.sellerController;

import java.util.Collection;

import org.springframework.stereotype.Component;

import com.example.demo.controller.UserDetails;
import com.example.demo.sellerEntity.SellerLogin;
@Component
public class SellerUserTwo  implements UserDetails{
	SellerLogin sellerLogin;
	public SellerUserTwo(SellerLogin sellerLogin) 
	{
		this.sellerLogin=sellerLogin;
	}
	@Override
	public Collection getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return sellerLogin.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return sellerLogin.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
