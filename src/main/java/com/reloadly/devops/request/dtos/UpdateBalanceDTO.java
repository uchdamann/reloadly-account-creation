package com.reloadly.devops.request.dtos;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class UpdateBalanceDTO {
	private String accountNumber;
	private BigDecimal amount;

}
