package com.reloadly.devops.transformers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.reloadly.devops.models.AccountDetails;
import com.reloadly.devops.models.Address;
import com.reloadly.devops.models.ContactInfo;
import com.reloadly.devops.models.PersonalInfo;
import com.reloadly.devops.models.User;
import com.reloadly.devops.request.dtos.AccountOpeningDTO;
import com.reloadly.devops.request.dtos.AddressDTO;
import com.reloadly.devops.request.dtos.ContactInfoDTO;
import com.reloadly.devops.request.dtos.PersonalInfoDTO;

@Component
public class ConverterUtil {
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public Address conv_AddressDTO_Address(AddressDTO addressDTO) {
		Address address = new Address();
		address.setAddress(addressDTO.getAddress());
		address.setState(addressDTO.getState());
		address.setCountry(addressDTO.getCountry());
		
		return address;
	}
	
	public PersonalInfo conv_PersonalInfoDTO_PersonalInfo(PersonalInfoDTO personalInfoDTO) {
		PersonalInfo personalInfo = new PersonalInfo();
		personalInfo.setFirstName(personalInfoDTO.getFirstName());
		personalInfo.setLastName(personalInfoDTO.getLastName());
		personalInfo.setAge(personalInfoDTO.getAge());
		personalInfo.setGender(personalInfoDTO.getGender());
		personalInfo.setMaritalStatus(personalInfoDTO.getMaritalStatus());
		
		return personalInfo;
	}
	
	public ContactInfo conv_ContactInfoDTO_ContactInfo(ContactInfoDTO contactInfoDTO) {
		ContactInfo contactInfo = new ContactInfo();
		contactInfo.setPhoneNumber(contactInfoDTO.getPhoneNumber());
		contactInfo.setEmail(contactInfoDTO.getEmail());
		
		return contactInfo;
	}
	
	public AccountDetails conv_AccountOpeningDTO_AccountDetails(AccountOpeningDTO accountOpeningDTO) {
		AccountDetails accountDetails = new AccountDetails();
		accountDetails.setAccountType(accountOpeningDTO.getAccountType());
		accountDetails.setBalance(accountOpeningDTO.getInitialDeposit());
		return accountDetails;
	}
	
	public User conv_AccountOpeningDTO_User(AccountOpeningDTO accountOpeningDTO) {
		User user = new User();
		user.setUsername(accountOpeningDTO.getContactInfoDTO().getEmail());
		user.setPassword(encoder.encode(accountOpeningDTO.getPassword()));
		
		return user;
	}

}
