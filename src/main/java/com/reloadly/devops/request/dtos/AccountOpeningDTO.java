package com.reloadly.devops.request.dtos;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.reloadly.devops.constants.AccountType;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccountOpeningDTO {
	@Valid
	@NotNull
	private PersonalInfoDTO personalInfoDTO;
	@Valid
	@NotNull
	private ContactInfoDTO contactInfoDTO;
	private AccountType accountType;
	@Min(5000L)
	private BigDecimal initialDeposit;
	private String password;
	
	public static AccountOpeningDTOBuilder builder() {
		return new AccountOpeningDTOBuilder();
	}

}
