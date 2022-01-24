package com.reloadly.devops.response.dtos;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginDTO {
	private UserDTO userDTO;
	
	public LoginDTOBuilder builder() {
		return new LoginDTOBuilder();
	}

}
