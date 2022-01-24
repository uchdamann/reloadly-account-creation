package com.reloadly.devops.request.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

import com.reloadly.devops.constants.Country;
import com.reloadly.devops.constants.State;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@SuppressWarnings("deprecation")
public class AddressDTO {
	@NotBlank(message = "Address is required!")
	@SafeHtml(whitelistType = WhiteListType.NONE)
	private String address;
	@NotNull(message = "State is required!")
	private State state;
	@NotNull(message = "Country is required!")
	private Country country;

}
