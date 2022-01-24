package com.reloadly.devops.models;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import javax.persistence.Version;

import com.reloadly.devops.constants.AccountType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AccountDetails extends CommonFields implements Serializable {
	private static final long serialVersionUID = 5296113976943950963L;
	@Column(unique = true)
	private String accountNumber;
	private BigDecimal balance;
	@Enumerated(EnumType.STRING)
	private AccountType accountType;
	@OneToOne
	private User user;
	@Version
	private Long version;
}
