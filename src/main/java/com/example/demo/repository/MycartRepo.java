package com.example.demo.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Item;
import com.example.demo.entity.Mycart;
import com.example.demo.projections.PopularItems;

public interface MycartRepo extends JpaRepository<Mycart, Integer>{

	byte countByUseridAndItemid_id(String username, int itemid);

	@Query(value = "select id,brand,category,itemname,price,qty,"
			+ "subcategory,primedisc,regulardisc,numberofdelivariesdone,rating as ratingstar "
			+ "from item "
			+ "where id in(select itemid_id from mycart where userid=?1)",nativeQuery = true)
	List<PopularItems> mycartItems(String username);
	//================================================
	//remove from cart
	@Modifying
	@Transactional
	void deleteByItemid(Item i1);

	@Modifying
	@Transactional
	void deleteByUseridAndItemid(String username, Item i1);

}
