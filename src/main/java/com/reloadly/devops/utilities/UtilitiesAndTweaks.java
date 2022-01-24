package com.reloadly.devops.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Base64;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.reloadly.devops.exceptions.AppException;
import com.reloadly.devops.models.User;
import com.reloadly.devops.repositories.UserRepo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UtilitiesAndTweaks {
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private AppProperties props;

	public void channelCodeHandler(HttpServletRequest req) {
		log.info("---->>> Initializing request source check");
		log.info("---->>> Authenticated user is: {}",
				req.getUserPrincipal() == null ? "No user" : req.getUserPrincipal().getName());

		final String channelCode = req.getHeader("ChannelCode");
		final String webRequest = props.getWebChannelCode();
		final String mobileConReq = props.getMobileConsumerChannelCode();

		if (channelCode != null) {
			if (channelCode.equals(webRequest)) {
				log.info("---->>> Request source: WEB BROWSER");
			} else if (channelCode.equals(mobileConReq)) {
				log.info("---->>> Request source:  MOBILE APP - CONSUMERS");
			} else {
				log.info("---->>> Error:  Invalid access code");
				throw new AppException("Access code is invalid"); // Access code is my ChannelCode
			}
		} else {
			log.info("---->>> Error:  Access code cannot be empty, blank or null");
			throw new AppException("Access code cannot be empty");
		}
	}

	

	public User checkAuthentication(HttpServletRequest req) {
		log.info("---->>> 1. Channel code check");
		channelCodeHandler(req); // run channel code check
		log.info("---->>> 2. Authenticaed user check");

		User user = null;
		String username = null;
		log.info("---->>> Checking request for authenticated principal via provided JWT token");

		if (req.getUserPrincipal() == null) {
			throw new AppException("No authenticated principal found");
		}

		username = req.getUserPrincipal().getName();

		if (username == null) {
			throw new AppException("No authenticated user found");
		}

		user = userRepo.findByUsername(username).orElseThrow(() -> new AppException("No authenticated user found"));

		return user;
	}
	
	public String base64String(MultipartFile file) {
		log.info("Original file size: " + file.getSize());

		if (!isValidExt(file)) {
			throw new AppException("File not supported");
		}

		File profilePixFile = buildFile(file);
		byte[] profilePixByteArray = toByteArray(profilePixFile);
		String profilePixString = Base64.getEncoder().encodeToString(profilePixByteArray);
		
		if(profilePixFile.delete())
		{
			log.info(profilePixFile.getName() + " deleted successfully");
		}
		
		return profilePixString;
	}

	public boolean isValidExt(MultipartFile file) {
		boolean isvalidExt = false;
		final String fileName = file.getOriginalFilename();

		if (fileName.endsWith(".png") || fileName.endsWith(".jpg") || fileName.endsWith(".gif")
				|| fileName.endsWith(".PNG") || fileName.endsWith(".JPG") || fileName.endsWith(".GIF")
				|| fileName.endsWith(".jpeg") || fileName.endsWith(".JPEG")) {
			isvalidExt = true;
		}

		return isvalidExt;
	}

	public File buildFile(MultipartFile file) 
	{
		int bytesRead = 0;
		File newFile = null;
		InputStream inputStream = null;
		OutputStream outputStream = null;
		
		try
		{
			byte[] buffer = new byte[8192];
			newFile = new File(file.getOriginalFilename());
			inputStream = file.getInputStream();
			outputStream = new FileOutputStream(newFile);
			
			while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1)
			{
				outputStream.write(buffer, 0, bytesRead);
			}
			
			log.info("---->>> FILE PATH: " + newFile.getAbsolutePath());
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(inputStream != null)
				{
					inputStream.close();
				}
			
				if(outputStream != null)
				{
					outputStream.close();
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
		return newFile;
	}
	
	private byte[] toByteArray(File file)
	{
		byte[] bytesArray = new byte[(int) file.length()];

		FileInputStream fis = null;
		try
		{
			fis = new FileInputStream(file);
			fis.read(bytesArray);
			fis.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (fis != null)
			{
				try 
				{
	                fis.close();
	            }
				catch (IOException e)
				{
	                e.printStackTrace();
	            }
	        }
		}

		return bytesArray;
	}
	
}