package com.reloadly.devops.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;

import com.reloadly.devops.constants.Gender;
import com.reloadly.devops.constants.MaritalStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PersonalInfo extends CommonFields implements Serializable{
	private static final long serialVersionUID = 8579562143745213578L;
	private String firstName;
	private String lastName;
	@Enumerated(EnumType.STRING)
	private MaritalStatus maritalStatus;
	private int age;
	@Enumerated(EnumType.STRING)
	private Gender gender;
	@OneToOne
	private User user;
}
