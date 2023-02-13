package com.example.demo.sellerRepo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.sellerEntity.SellerLogin;

public interface SellerLoginRepo extends JpaRepository<SellerLogin, String>{

	byte countByUsername(String username);

	

}
