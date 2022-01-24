package com.reloadly.devops.response.dtos;

import com.reloadly.devops.constants.Country;
import com.reloadly.devops.constants.State;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddressDTO {
	private String address;
	private State state;
	private Country country;

}
