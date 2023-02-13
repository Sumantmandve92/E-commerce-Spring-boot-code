/**
 * 
 */
package com.example.demo.sellerRepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.sellerEntity.File;

/**
 * @author Sumant
 * Repositories of entities(present in entity package)
 */
@Repository
public interface FileRepo  extends JpaRepository<File, String>{

}
