package com.reloadly.devops.response.dtos;

import com.reloadly.devops.constants.Gender;
import com.reloadly.devops.constants.MaritalStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PersonalInfoDTO {
	private String fullName;
	private MaritalStatus maritalStatus;
	private Gender gender;
	private int age;

}
