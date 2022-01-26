package com.reloadly.devops.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.reloadly.devops.request.dtos.UpdateBalanceDTO;
import com.reloadly.devops.response.dtos.ResponseDTO;
import com.reloadly.devops.response.dtos.UpdatedAccountDTO;
import com.reloadly.devops.services.UserService;
import com.reloadly.devops.utilities.UtilitiesAndTweaks;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@RestController
@RequestMapping("api/account-management/v1")
@SecurityRequirement(name = "ChannelCode")
@SecurityRequirement(name = "Authorization")
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
	
	@PostMapping("/getfirstname/{username}")
	public Map<String, Object> validateUser(@PathVariable String username, HttpServletRequest req) {
		log.info("--->> Initializing user validation by username");
		util.channelCodeHandler(req);
		Map<String, Object> map = new HashMap<>();
		User user = userRepo.findByUsername(username).orElseThrow(() -> new InvalidUserException());
		map.put("firstName", user.getPersonalInfo().getFirstName());
		map.put("valid", true);
		
		return map;
	}
	
	@PostMapping("/getbalance/{accountNumber}")
	public Map<String, Object> validateAccountNumber(@PathVariable String accountNumber, HttpServletRequest req) {

		log.info("--->> Initializing user validation by account number");
		util.channelCodeHandler(req);
		Map<String, Object> map = new HashMap<>();
		AccountDetails accountDetails = accountDetailsRepo.findByAccountNumber(accountNumber)
				.orElseThrow(() -> new InvalidUserException("Account number does not exist"));
		map.put("valid", true);
		map.put("balance", accountDetails.getBalance());
		
		return map;
	}
	
	@PostMapping("/update-balance")
	public ResponseDTO<UpdatedAccountDTO> updateBalance(@RequestBody UpdateBalanceDTO updateBalanceDTO, 
			HttpServletRequest req) {
		log.info("--->> Initializing update of account balance");
		util.channelCodeHandler(req);
		
		return userService.updateAccount(updateBalanceDTO);
	}
	
}