package com.reloadly.devops.request.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginDetailsDTO {
	@Email
	private String username;
	@NotBlank
	private String password;
	@NotBlank
	private String location;
}
