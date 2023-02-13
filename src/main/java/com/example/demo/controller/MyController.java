package com.example.demo.controller;

import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import javax.persistence.OneToOne;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.example.demo.entity.Item;
import com.example.demo.entity.KeyWithStatus;
import com.example.demo.entity.Login;
import com.example.demo.entity.Mycart;
import com.example.demo.entity.Myorders;
import com.example.demo.projections.Order;
import com.example.demo.projections.PopularItems;
import com.example.demo.projections.SearchProduct;
import com.example.demo.repository.ItemRepo;
import com.example.demo.repository.LoginRepo;
import com.example.demo.repository.MycartRepo;
import com.example.demo.repository.MyordersRepo;
import com.example.demo.sellerEntity.File;
import com.example.demo.sellerRepo.FileRepo;

@RestController
@CrossOrigin

public class MyController {

	@Autowired
	private LoginRepo logRepo;
	@Autowired
	private ItemRepo itemRepo;
	@Autowired
	private JavaMailSender mailsender;
	@Autowired
	private FileRepo fileRepo;
	@Autowired
	private JwtUtil jwt;
	@Autowired
	private Login login;
	@Autowired
	private UserDetails userDetail;

	@Autowired
	private MycartRepo cartRepo;
	@Autowired
	MyordersRepo orderRepo;
	//	.===============================================================================
	//	handler
	@RequestMapping("createAccount")
	public byte register(@RequestBody Login r1) 
	{
		try {

			byte cnt=logRepo.countByUsername(r1.getUsername());
			if(cnt==1) 
			{
//				already registered
				return -1;
			}
			else 
			{
//				new register
				logRepo.save(r1);
				return 1;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	@RequestMapping("login")
	public KeyWithStatus login(@RequestBody Login r1) 
	{
		try {
			byte cnt=logRepo.countByUsername(r1.getUsername());
			if(cnt==1) 
			{
				Login r2=logRepo.findById(r1.getUsername()).get();
				if(r2.getPassword().equals(r1.getPassword())) 
				{
					//					System.out.println(r1.getUsername());
					login.setPassword(r1.getPassword());
					login.setUsername(r1.getUsername());
					UserDetails userDetails=new UserTwo(login);
					String tokan=jwt.generateToken(userDetails);
					/*login successfully*/
					KeyWithStatus kws=new KeyWithStatus(tokan,1,null,r2.getEmail(),r2.getDeliveryaddress(),r2.getPincode(),r2.getMywallet(),null);
					return kws;	
				}
				else 
				{
					/*wrong password*/
					return new KeyWithStatus(null,-1,null,null,null,0,0,null);
				}
			}
			else 
			{
				/*wrong username*/
				return new KeyWithStatus(null,-2,null,null,null,0,0,null);
			}

		} catch (Exception e) {
			e.printStackTrace();
			/*SWR*/
			return new KeyWithStatus(null,0, null,null,null,0,0,null);
		}
	}
	@RequestMapping("listOfAvailableItem")
	public List<Item> listOfAvailableItem()
	{

		return itemRepo.findAll();

	}
	//	=====================================================================================
	@RequestMapping("addToCart/{tokan}")
	synchronized public boolean addToCart(@PathVariable String tokan,@RequestBody int itemId) 
	{
		try {

			String username=jwt.extractUsername(tokan);
			Item i1=new Item();
			i1.setId(itemId);
			cartRepo.save(new Mycart(0,username,i1,new Date(),0));



			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	//	======================================================================================
	@RequestMapping("isAddedTocart/{tokan}")
	public boolean isAddedTocart(@PathVariable String tokan,@RequestBody int itemid) 
	{
		String username=jwt.extractUsername(tokan);
		byte cnt=cartRepo.countByUseridAndItemid_id(username,itemid);
		if(cnt==1) 
		{
			return true;
		}
		else 
		{
			return false;
		}
	}
	@RequestMapping("removeFromCart/{tokan}")
	public boolean removeFromCart(@PathVariable String tokan,@RequestBody int itemId) 
	{
		try {
			String username=jwt.extractUsername(tokan);
			Item i1=new Item();
			i1.setId(itemId);
			cartRepo.deleteByUseridAndItemid(username,i1);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
			
	}
	@RequestMapping("getmycart/{tokan}")
	public List<PopularItems> getmycart(@PathVariable String tokan) {

		String username=jwt.extractUsername(tokan);
		return cartRepo.mycartItems(username);
	}
	//	===============================================================================================
	@RequestMapping("buy/{numberOfProducts}/{tokan}")
	synchronized public long buy(@PathVariable int numberOfProducts,@PathVariable String tokan,@RequestBody int itemId) 
	{
		String username=jwt.extractUsername(tokan);
		try {
			Item item=itemRepo.findById(itemId).get();
			if((item.getQty()-numberOfProducts)>=0) 
			{
				//======================================================================prepare order
				
	
				//delivery date should be calculated from another method  by considering address 
				
				Myorders myorder=new Myorders();
				myorder.setUsername(username);
				myorder.setItem(item);
				myorder.setQty(numberOfProducts);
				myorder.setOrderplacedon(new Date());
				myorder.setNetpriceoforder((long)item.getPrice()*(100-item.getPrimedisc())/100);
				orderRepo.save(myorder);
				//======================================================================update qty of item
				item.setQty((byte)(item.getQty()-numberOfProducts));
				itemRepo.save(item);
				//======================================================================update wallet of user
				Login r2=logRepo.findById(username).get();
				r2.setMywallet((long)(r2.getMywallet()-numberOfProducts*item.getPrice()));
				logRepo.save(r2);
				//========================================================================remove from cart
				cartRepo.deleteByItemid(item);
				//======================================================================send mail to user
				LocalDateTime  ldt=LocalDateTime.now();
				DateTimeFormatter df =DateTimeFormatter.ofPattern("dd-MM-yyyy");
				String mydate=ldt.format(df);
				String body="You purchased an item "+item.getItemname()+" and brand "+item.getBrand()+" of price "+item.getPrice();
				sendEmail( r2.getEmail(),body,"welcome to SWR Shopping Mall");
				//return order id
				return (long)Math.abs((username+new Date().toString()+itemId).hashCode());//orderid
			}
			else 
			{
				System.out.println("check qty ");
				return -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -2;
		}
	}

	public void sendEmail(String toEmail,String body,String subject) 
	{
		SimpleMailMessage message=new SimpleMailMessage();
		message.setFrom("swrshoppingmall2022@gmail.com");
		message.setTo(toEmail);
		message.setText(body);
		message.setSubject(subject);
		mailsender.send(message);
		System.out.println(message);

	}
	//	======================================================================================
	@RequestMapping("getMyOrders/{tokan}")
	synchronized public List<Order> myOrders(@PathVariable String tokan )
	{
		try {
			String username=jwt.extractUsername(tokan);
			return logRepo.getMyOrders(username);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@RequestMapping("cancelOrder/{tokan}")
	public boolean cancelOrder(@PathVariable String tokan,@RequestBody int orderId) 
	{
		try {
			String username=jwt.extractUsername(tokan);
			Myorders order= orderRepo.findById(orderId).get();
			Login user=logRepo.findById(username).get();
			
			orderRepo.deleteById(orderId);
//====================================return money
			user.setMywallet(user.getMywallet()+order.getNetpriceoforder());
			logRepo.save(user);
//=============================================			
			


			String body="You canceled order  an item "+order.getItem().getItemname()+" of id "+order.getId()+" and brand "+order.getItem().getBrand()+" of price "+order.getNetpriceoforder();

			sendEmail( user.getEmail(),body,"welcome to SWR Shopping Mall");

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	@RequestMapping("getPopularItems")
	public List<PopularItems> getPopularItem()
	{
		try {
			return logRepo.getpopularItems();
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}
	}
	@RequestMapping("getItemsByCategory/{category}")
	public List<PopularItems> getItemsByCategory(@PathVariable String category)
	{
		try {
			return logRepo.getItemsByCategory(category);

		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}
	}
	@RequestMapping("searchProducts/{search}")
	public List<PopularItems> searchProducts(@PathVariable String search) 
	{
		return logRepo.searchProducts(search);
	}

}
