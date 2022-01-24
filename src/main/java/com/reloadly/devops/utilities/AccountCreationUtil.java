package com.reloadly.devops.utilities;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.reloadly.devops.repositories.AccountDetailsRepo;

@Component
public class AccountCreationUtil {
	@Autowired
	private AppProperties prop;
	@Autowired
	private AccountDetailsRepo accountDetailsRepo;
		
	public String generateAccountNumber() {
		StringBuilder sb = new StringBuilder();
		SecureRandom random = new SecureRandom();
		char[] accountNumberArray = prop.getAccountCharacters().toCharArray();
		
		for (int count = 0; count < prop.getAccountNumberLength(); count++) {
			sb.append(accountNumberArray[random.nextInt(prop.getAccountNumberLength())]);
		}

		return sb.toString();
	}
	
	public String accountNumber() {
		String accountNumber = null;
		do {
			accountNumber = generateAccountNumber();
		}
		while (accountDetailsRepo.existsByAccountNumber(accountNumber));
		
		return accountNumber;
	}
	
}
