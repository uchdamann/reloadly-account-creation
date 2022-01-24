package com.reloadly.devops.models;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ContactInfo extends CommonFields implements Serializable{
	private static final long serialVersionUID = -5074369594114840364L;
	private String phoneNumber;
	@OneToOne(mappedBy = "contactInfo", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Address address;
	@OneToOne
	private User user;
	private String email;

}