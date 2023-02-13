package com.example.demo.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Login;
import com.example.demo.projections.Order;
import com.example.demo.projections.PopularItems;

public interface LoginRepo extends JpaRepository<Login, String>{

	byte countByUsername(String username);
//================================================================================================================
	@Query(value = " select id,brand,category,itemname,price,qty,"
			+ "subcategory,primedisc,regulardisc,numberofdelivariesdone,ratingstar,allitemdetails "
			+ "from item join (select item_id, avg(star) as ratingstar "
			+ "from (item_reviews i join reviews r)  "
			+ "where i.reviews_id=r.id  group by item_id ) ir "
			+ "where item.id=ir.item_id  "
			+ "order by ratingstar desc "
			+ "limit 10"
			,nativeQuery = true)
	List<PopularItems> getpopularItems();
//	===============================================================================================================
@Query(value = " select id,brand,category,itemname,price,qty,subcategory,primedisc,"
		+ "regulardisc,numberofdelivariesdone,rating as ratingstar,allitemdetails"
		+ " from item where category=?1 ",nativeQuery = true)
	List<PopularItems> getItemsByCategory(String category);
//==========================================================================================
@Query(value = "select mo.id,orderplacedon as orderdate,mo.qty,brand,category,itemname "
		+ "from myorders mo join item i "
		+ "on mo.item_id=i.id "
		+ "where username=?1" ,nativeQuery = true)
List<Order> getMyOrders(String username);
//=======================================================================================
@Query(value = "select id,brand,category,itemname,price,qty,subcategory,primedisc,"
		+ "regulardisc,numberofdelivariesdone,rating as ratingstar,  allitemdetails "
		+ "from item "
		+ "where brand like %?1% or category like %?1% "
		+ "or itemname like %?1% or subcategory like %?1% "
		+ "limit 25",
		nativeQuery = true)
List<PopularItems> searchProducts(String search);

}
