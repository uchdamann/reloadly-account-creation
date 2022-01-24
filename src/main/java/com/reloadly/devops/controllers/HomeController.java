package com.reloadly.devops.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reloadly.devops.exceptions.InvalidUserException;
import com.reloadly.devops.models.AccountDetails;
import com.reloadly.devops.models.User;
import com.reloadly.devops.repositories.AccountDetailsRepo;
import com.reloadly.devops.repositories.UserRepo;
import com.reloadly.devops.request.dtos.AccountOpeningDTO;
import com.reloadly.devops.request.dtos.LoginDetailsDTO;
import com.reloadly.devops.request.dtos.UpdateBalanceDTO;
import com.reloadly.devops.response.dtos.CreatedAccountDTO;
import com.reloadly.devops.response.dtos.OauthDTO;
import com.reloadly.devops.response.dtos.ResponseDTO;
import com.reloadly.devops.services.UserService;
import com.reloadly.devops.utilities.UtilitiesAndTweaks;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
@Validated
@RestController
@RequestMapping("api/account-management/v1")
@SecurityRequirement(name = "ChannelCode")
@CrossOrigin(origins = "*", maxAge = 3600)
public class HomeController {
	@Autowired
	private UserService userService;
	@Autowired
	private UtilitiesAndTweaks util;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private AccountDetailsRepo accountDetailsRepo;
	
	@PostMapping("/create-account")
	public ResponseDTO<CreatedAccountDTO> createAccount(@RequestBody @Valid AccountOpeningDTO accountOpeningDTO, 
			HttpServletRequest req){
		util.channelCodeHandler(req);
		
		return userService.createAccount(accountOpeningDTO);
	}
	
	@PostMapping("/login")
	public ResponseDTO<OauthDTO> login(@RequestBody @Valid LoginDetailsDTO loginDetailsDTo,	HttpServletRequest req){
		util.channelCodeHandler(req);
		
		return userService.login(loginDetailsDTo);
	}
	
	@GetMapping("/validate-user/{username}")
	public Map<String, Object> validateUser(@PathVariable String username, HttpServletRequest req) {
		util.channelCodeHandler(req);
		Map<String, Object> map = new HashMap<>();
		User user = userRepo.findByUsername(username).orElseThrow(() -> new InvalidUserException());
		map.put("firstName", user.getPersonalInfo().getFirstName());
		map.put("valid", true);
		
		return map;
	}
	
	@GetMapping("/validate-account-number/{accountNumber}")
	public Map<String, Object> validateAccountNumber(@PathVariable String accountNumber, HttpServletRequest req) {
		util.channelCodeHandler(req);
		Map<String, Object> map = new HashMap<>();
		AccountDetails accountDetails = accountDetailsRepo.findByAccountNumber(accountNumber)
				.orElseThrow(() -> new InvalidUserException("Account number does not exist"));
		map.put("valid", true);
		map.put("balance", accountDetails.getBalance());
		
		return map;
	}	
	
	@PatchMapping("/update-balance")
	public ResponseDTO<String> updateBalance(@RequestBody UpdateBalanceDTO updateBalanceDTO, 
			HttpServletRequest req) {
		util.channelCodeHandler(req);
		
		return userService.updateAccount(updateBalanceDTO);
	}	
	
}