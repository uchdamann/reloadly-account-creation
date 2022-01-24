package com.reloadly.devops.response.dtos;

import java.math.BigDecimal;

import com.reloadly.devops.constants.AccountType;

import lombok.Data;

@Data
public class AccountDetailsDTO {
	private String accountNumber;
	private BigDecimal balance;
	private AccountType accountType;

}
