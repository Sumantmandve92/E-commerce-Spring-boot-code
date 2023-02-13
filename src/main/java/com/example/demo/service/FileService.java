/**

 * 
 */
package com.example.demo.service;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.sellerEntity.File;

/**
 * @author Sumant

 *
 */

public interface FileService {

	File saveFileToDataBaseAfterServicing(MultipartFile f1) throws Exception;


		
		
	

}
