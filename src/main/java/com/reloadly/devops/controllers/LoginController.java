package com.reloadly.devops.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reloadly.devops.request.dtos.AccountOpeningDTO;
import com.reloadly.devops.request.dtos.LoginDetailsDTO;
import com.reloadly.devops.response.dtos.CreatedAccountDTO;
import com.reloadly.devops.response.dtos.OauthDTO;
import com.reloadly.devops.response.dtos.ResponseDTO;
import com.reloadly.devops.services.UserService;
import com.reloadly.devops.utilities.UtilitiesAndTweaks;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@RestController
@RequestMapping("api/account/v1")
@SecurityRequirement(name = "ChannelCode")
@CrossOrigin(origins = "*", maxAge = 3600)
public class LoginController {
	@Autowired
	private UserService userService;
	@Autowired
	private UtilitiesAndTweaks util;
	
	@PostMapping("/create-account")
	public ResponseDTO<CreatedAccountDTO> createAccount(@RequestBody @Valid AccountOpeningDTO accountOpeningDTO, 
			HttpServletRequest req){
		log.info("--->> Initializing account creation");
		util.channelCodeHandler(req);
		
		return userService.createAccount(accountOpeningDTO);
	}
	
	@PostMapping("/login")
	public ResponseDTO<OauthDTO> login(@RequestBody @Valid LoginDetailsDTO loginDetailsDTO,	HttpServletRequest req){
		log.info("--->> Initializing user login");
		util.channelCodeHandler(req);
		
		return userService.login(loginDetailsDTO);
	}
		
}