package com.example.demo.sellerController;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.zip.Deflater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.controller.JwtUtil;
import com.example.demo.controller.UserDetails;
import com.example.demo.entity.Item;
import com.example.demo.entity.KeyWithStatus;
import com.example.demo.repository.ItemRepo;
import com.example.demo.sellerEntity.File;
import com.example.demo.sellerEntity.SellerLogin;
import com.example.demo.sellerRepo.FileRepo;
import com.example.demo.sellerRepo.SellerLoginRepo;
import com.example.demo.service.FileService;

import io.jsonwebtoken.io.IOException;

@RestController
@CrossOrigin
public class SellerController {



	@Autowired
	private SellerLoginRepo sellerlogRepo;


	@Autowired
	private ItemRepo itemRepo;
	@Autowired
	private JavaMailSender mailsender;
	@Autowired
	FileRepo fileRepo;
	@Autowired
	JwtUtil jwt;
	@Autowired
	SellerLogin sellerlogin;
	@Autowired
	UserDetails userDetail;
	@Autowired
	FileService fileservice;
	//	.===============================================================================
	//	handler
	@RequestMapping("sell/{tokan}")
	public void sell(@PathVariable String tokan,@RequestBody Item item) 
	{
		

	}
//	.===============================================================================
	//	handler
	@RequestMapping("c2reateAccountForSeller")
	public byte register(@RequestBody SellerLogin r1) 
	{
		try {

			byte cnt=sellerlogRepo.countByUsername(r1.getUsername());
			if(cnt==1) 
			{
				return -1;
			}
			else 
			{
				sellerlogRepo.save(r1);
				return 1;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	@RequestMapping("sellerLogin")
	public KeyWithStatus login(@RequestBody SellerLogin r1) 
	{
		try {
			byte cnt=sellerlogRepo.countByUsername(r1.getUsername());
			if(cnt==1) 
			{
				SellerLogin r2=sellerlogRepo.findById(r1.getUsername()).get();
				if(r2.getPassword().equals(r1.getPassword())) 
				{
//					System.out.println(r1.getUsername());
					sellerlogin.setPassword(r1.getPassword());
					sellerlogin.setUsername(r1.getUsername());
					
					UserDetails userDetails=new SellerUserTwo(sellerlogin);
					String tokan=jwt.generateToken(userDetails);

					/*login successfully*/
					KeyWithStatus kws=new KeyWithStatus(1,tokan,r2.getProfile());
					return kws;	
				}
				else 
				{
					/*wrong password*/
					return new KeyWithStatus(-1,null,null);
				}
			

			}
			else 
			{
				/*wrong username*/
				return new KeyWithStatus(-2,null,null);
			}

		} catch (Exception e) {
			e.printStackTrace();
			/*SWR*/
			return new KeyWithStatus(0,null,null);
		}
	}
	@RequestMapping("s2ellitem/{tokan}")
	public Item addItem(@PathVariable String tokan,@RequestBody Item i1) 
	{
		try {
//			LocalDateTime  ldt=LocalDateTime.now();
//			DateTimeFormatter df =DateTimeFormatter.ofPattern("dd-MM-yyyy");
//			String mydate=ldt.format(df);
			Date d= new Date();
			i1.setDateOfupload(d);
			Item i2=itemRepo.save(i1);
			String username=jwt.extractUsername(tokan);
			SellerLogin s1=sellerlogRepo.findById(username).get();
			s1.getItemList().add(i2);
			sellerlogRepo.save(s1);
			return i2;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
			
		}
		
	}
	@RequestMapping("g2etAlluploadedItems/{tokan}")
	public List<Item> getMyAllUploadedItem(@PathVariable String tokan)
	{
		try {
			String username=jwt.extractUsername(tokan);
			SellerLogin s1=sellerlogRepo.findById(username).get();
			return s1.getItemList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
//	=================================================================================================================================================
	@Autowired
	FileRepo fileRepo2;
	
	@RequestMapping("u2ploadfile/{itemid}/{tokan}")
	public BodyBuilder uploadImage(@RequestParam("imageFile") MultipartFile file, @PathVariable int itemid,@PathVariable String tokan) throws IOException
	{
		try {
			System.out.println("Original image byte size : "+file.getBytes().length);
			File img=new File(0,file.getOriginalFilename(),file.getContentType(),compressedBytes(file.getBytes()));
			fileRepo.save(img);
			Item i1=itemRepo.findById(itemid).get();
			i1.setFile(img);
			itemRepo.save(i1);
			return ResponseEntity.status(HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	private byte[] compressedBytes(byte[] bytes) {
	
		Deflater deflater=new Deflater();
		deflater.setInput(bytes);
		deflater.finish();
		ByteArrayOutputStream outputStream= new ByteArrayOutputStream(bytes.length);
		byte[] buffer = new byte[1024];
		while (!deflater.finished()) {
			int count = deflater.deflate(buffer);
			outputStream.write(buffer, 0, count);
		}
		try {
			outputStream.close();
		} catch (Exception e) {
		}
		System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

		return outputStream.toByteArray();
		
	}
//	=================================================================================================================================================

}
