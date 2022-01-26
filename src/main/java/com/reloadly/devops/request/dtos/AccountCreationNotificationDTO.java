package com.reloadly.devops.request.dtos;

import java.math.BigDecimal;

import com.reloadly.devops.constants.AccountType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AccountCreationNotificationDTO extends NotificationDTO {
	private String accountNumber;
	private BigDecimal initialBalance;
	private String firstname;
	private String lastname;
	private AccountType accountType;
}
