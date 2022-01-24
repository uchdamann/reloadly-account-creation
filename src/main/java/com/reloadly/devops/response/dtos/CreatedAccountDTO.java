package com.reloadly.devops.response.dtos;

import java.math.BigDecimal;

import com.reloadly.devops.constants.AccountType;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreatedAccountDTO {
	private String username;
	private String accountNumber;
	private AccountType accountType;
	private BigDecimal accountBalance;
	
	public static CreatedAccountDTOBuilder builder() {
		return new CreatedAccountDTOBuilder();
	}

}
