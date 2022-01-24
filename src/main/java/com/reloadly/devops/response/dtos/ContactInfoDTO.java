package com.reloadly.devops.response.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContactInfoDTO {
	private String phoneNumber;
	private String email;
	private AddressDTO addressDTO;

}
