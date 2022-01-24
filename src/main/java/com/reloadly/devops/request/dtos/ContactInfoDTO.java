package com.reloadly.devops.request.dtos;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@SuppressWarnings("deprecation")
public class ContactInfoDTO {
	@NotBlank(message = "Phone Number is required!")
	@SafeHtml(whitelistType = WhiteListType.NONE)
	private String phoneNumber;
	@Valid
	@NotNull(message = "Address is required!")
	private AddressDTO addressDTO;
	@Email
	private String email;
}