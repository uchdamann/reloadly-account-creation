package com.reloadly.devops.services;

import java.util.Optional;

import com.reloadly.devops.models.Address;
import com.reloadly.devops.models.ContactInfo;
import com.reloadly.devops.models.PersonalInfo;
import com.reloadly.devops.models.User;
import com.reloadly.devops.request.dtos.AccountOpeningDTO;
import com.reloadly.devops.request.dtos.AccountUpdateDTO;
import com.reloadly.devops.request.dtos.AddressDTO;
import com.reloadly.devops.request.dtos.ContactInfoDTO;
import com.reloadly.devops.request.dtos.LoginDetailsDTO;
import com.reloadly.devops.request.dtos.PersonalInfoDTO;
import com.reloadly.devops.response.dtos.CreatedAccountDTO;
import com.reloadly.devops.response.dtos.OauthDTO;
import com.reloadly.devops.response.dtos.ResponseDTO;
import com.reloadly.devops.response.dtos.UpdatedAccountDTO;

public interface UserService {
	public ResponseDTO<CreatedAccountDTO> createAccount(AccountOpeningDTO accountOpeningDTO);
	public Optional<ContactInfo> createContactInfo(ContactInfoDTO contactInfoDTO, User user);
	public Optional<PersonalInfo> createPersonalInfo(PersonalInfoDTO personalInfoDTO, User user);
	public Optional<Address> createAddressInfo(AddressDTO addressDTO, ContactInfo contactInfo);
	public ResponseDTO<UpdatedAccountDTO> updateAccount(AccountUpdateDTO accountUpdate);
	public ResponseDTO<OauthDTO> login(LoginDetailsDTO loginDetailsDTo);
}