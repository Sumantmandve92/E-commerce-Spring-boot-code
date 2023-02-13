package com.example.demo.controller;

import java.io.IOException;

import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.entity.Login;
import com.example.demo.repository.LoginRepo;
import com.example.demo.sellerController.SellerUserTwo;
import com.example.demo.sellerEntity.SellerLogin;
import com.example.demo.sellerRepo.SellerLoginRepo;
@Component
public class MySecurityGuard  extends OncePerRequestFilter{

	@Autowired
	JwtUtil jwt;
	@Autowired
	LoginRepo logRepo;
	@Autowired
	SellerLoginRepo sellerloginRepo;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String path=request.getRequestURI();
		System.out.println(path);
		if(path.startsWith("/login")
				||path.startsWith("/createAccount")
				||path.startsWith("/getPopularItems")
				||path.startsWith("/c2reateAccountForSeller")
				||path.startsWith("/sellerLogin")
				||path.startsWith("/getItemsByCategory")
				||path.startsWith("/searchProducts")) 
		{
			filterChain.doFilter(request, response);
		}
		else 
		{
			String[] pathdetails=path.split("/");
			String tokan=pathdetails[pathdetails.length-1];
			String username=jwt.extractUsername(tokan);
			System.out.println(pathdetails[1]+" "+pathdetails[2]);
			if(path.charAt(2)=='2') 
			{
				SellerLogin sellerlogin=sellerloginRepo.findById(username).get();
				SellerUserTwo selleru2=new SellerUserTwo(sellerlogin);
				if(jwt.validateToken(tokan, selleru2)) 
				{
					request.setAttribute("username", sellerlogin.getUsername());
					filterChain.doFilter(request, response);					
				}
				else 
				{
					throw new IOException();
				}
			}
			else 
			{
				Login r1=logRepo.findById(username).get();
				UserTwo userTwo=new UserTwo(r1);
				if(jwt.validateToken(tokan, userTwo)) 
				{
					request.setAttribute("username", r1.getUsername());
					filterChain.doFilter(request, response);
				}
				else 
				{
					throw new IOException();
				}
			}

			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
//			
////			String tokan=request.getInputStream().toString();
//			String ipAddress=request.getLocalAddr();
//
//			String HostNameOfIP=request.getLocalName();
//			String headerTokan=request.getHeader(tokan);
//
//			String httpRequestType=request.getMethod();//get/post/put
//			String tokan1=request.getParameter(tokan);
//			String lastProxy=request.getRemoteAddr();
//			String fullyQualifiedNameOfClient=request.getRemoteHost();
//			String nameOfServer=request.getServerName();
//			Enumeration<String> toka=request.getParameterNames();
//			ServletInputStream body= request.getInputStream();
////			
//			System.out.println("ipAddress="+ipAddress);
//			System.out.println("HostNameOfIP="+HostNameOfIP);
//			System.out.println("tokan1="+headerTokan);
//			System.out.println("lastProxy="+lastProxy);
//			System.out.println("fullyQualifiedNameOfClient="+fullyQualifiedNameOfClient);
//			System.out.println("toka="+toka);
//			System.out.println("body="+body.toString());
//





			
			
		}
		
	}

}

