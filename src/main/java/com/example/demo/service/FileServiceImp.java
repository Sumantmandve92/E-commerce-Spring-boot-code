package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.sellerEntity.File;
import com.example.demo.sellerRepo.FileRepo;

@Service
public class FileServiceImp implements FileService
{

	@Autowired
	FileRepo fRepo;
	@Override
	public File saveFileToDataBaseAfterServicing(MultipartFile file) throws Exception
	{
		String fileName=StringUtils.cleanPath(file.getOriginalFilename());
		//validate file
		try {
			if(fileName.contains("..")) 
			{
				throw new Exception("File name contains invalid path sequence "+fileName);
			}

			File f1=new File( fileName,file.getContentType(),file.getBytes());
			return fRepo.save(f1);

		}
		catch (Exception e) 
		{

			throw new Exception("Could not save file "+fileName);
		}

	}

}
