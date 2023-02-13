package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Myorders;

public interface MyordersRepo extends JpaRepository<Myorders, Integer>{

}
