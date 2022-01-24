package com.reloadly.devops.response.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {
	private AccountDetailsDTO accountDetailsDTO;
	private PersonalInfoDTO personalInfoDTO;
	private ContactInfoDTO contactInfoDTO;

}
