package com.reloadly.devops.request.dtos;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

import com.reloadly.devops.constants.Gender;
import com.reloadly.devops.constants.MaritalStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@SuppressWarnings("deprecation")
public class PersonalInfoDTO {

	@NotBlank(message = "First name is required!")
	@SafeHtml(whitelistType = WhiteListType.NONE)
	private String firstName;
	@NotBlank(message = "Last name is required!")
	@SafeHtml(whitelistType = WhiteListType.NONE)
	private String lastName;
	@NotNull(message = "Marital status is required!")
	private MaritalStatus maritalStatus;
	@Min(value = 16L)
	private int age;
	@NotNull(message = "Gender is required!")
	private Gender gender;
}
