package com.reloadly.devops.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;

import com.reloadly.devops.constants.Country;
import com.reloadly.devops.constants.State;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class Address extends CommonFields implements Serializable {
	private static final long serialVersionUID = 8520163842716695150L;
	private String address;
	@Enumerated(EnumType.STRING)
	private State state;
	@Enumerated(EnumType.STRING)
	private Country country;
	@OneToOne
	private ContactInfo contactInfo;

}
