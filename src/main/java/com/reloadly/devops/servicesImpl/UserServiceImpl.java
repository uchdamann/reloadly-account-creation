package com.reloadly.devops.servicesImpl;

import java.util.Date;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.reloadly.devops.constants.NotificationType.*;

import com.reloadly.devops.exceptions.AppException;
import com.reloadly.devops.models.AccountDetails;
import com.reloadly.devops.models.Address;
import com.reloadly.devops.models.ContactInfo;
import com.reloadly.devops.models.PersonalInfo;
import com.reloadly.devops.models.User;
import com.reloadly.devops.repositories.AccountDetailsRepo;
import com.reloadly.devops.repositories.AddressRepo;
import com.reloadly.devops.repositories.ContactInfoRepo;
import com.reloadly.devops.repositories.PersonalInfoRepo;
import com.reloadly.devops.repositories.UserRepo;
import com.reloadly.devops.request.dtos.AccountCreationNotificationDTO;
import com.reloadly.devops.request.dtos.AccountOpeningDTO;
import com.reloadly.devops.request.dtos.AddressDTO;
import com.reloadly.devops.request.dtos.ContactInfoDTO;
import com.reloadly.devops.request.dtos.LoginDetailsDTO;
import com.reloadly.devops.request.dtos.LoginNotificationDTO;
import com.reloadly.devops.request.dtos.PersonalInfoDTO;
import com.reloadly.devops.request.dtos.UpdateBalanceDTO;
import com.reloadly.devops.response.dtos.CreatedAccountDTO;
import com.reloadly.devops.response.dtos.OauthDTO;
import com.reloadly.devops.response.dtos.ResponseDTO;
import com.reloadly.devops.response.dtos.UpdatedAccountDTO;
import com.reloadly.devops.services.NotificationService;
import com.reloadly.devops.services.UserService;
import com.reloadly.devops.transformers.ConverterUtil;
import com.reloadly.devops.utilities.AccountCreationUtil;
import com.reloadly.devops.utilities.ExternalCalls;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.reloadly.devops.constants.ResponseMessages.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final ConverterUtil converterUtil;
	private final UserRepo userRepo;
	private final AccountDetailsRepo accountDetailsRepo;
	private final ContactInfoRepo contactInfoRepo;
	private final PersonalInfoRepo personalInfoRepo;
	private final AddressRepo addressRepo;
	private final AccountCreationUtil accountCreationUtil;
	private final BCryptPasswordEncoder encoder;
	private final ExternalCalls extCalls;
	private final NotificationService notification;

	@Override
	@Transactional
	public ResponseDTO<CreatedAccountDTO> createAccount(AccountOpeningDTO accountOpeningDTO) {
				
		log.info("--->> Commencing account creation process");
		User user = converterUtil.conv_AccountOpeningDTO_User(accountOpeningDTO);
		user = userRepo.save(user);
		log.info("--->> Saved user details of " + user.getUsername());
		
		ContactInfo contactInfo = createContactInfo(accountOpeningDTO.getContactInfoDTO(), user).get();
		contactInfo = contactInfoRepo.save(contactInfo);
		log.info("--->> Saved contact details of " + user.getUsername());
		
		PersonalInfo personalInfo = createPersonalInfo(accountOpeningDTO.getPersonalInfoDTO(), user).get();
		personalInfo = personalInfoRepo.save(personalInfo);
		log.info("--->> Saved personal details of " + user.getUsername());
		
		Address address = createAddressInfo(accountOpeningDTO.getContactInfoDTO().getAddressDTO(), contactInfo).get();
		address = addressRepo.save(address);
		log.info("--->> Saved address details of " + user.getUsername());
		
		String accountNumber = accountCreationUtil.accountNumber();
		log.info("--->> Generated account number: " + accountNumber);
		AccountDetails accountDetails = converterUtil.conv_AccountOpeningDTO_AccountDetails(accountOpeningDTO);
		accountDetails.setAccountNumber(accountNumber);
		accountDetails.setUser(user);
		accountDetails = accountDetailsRepo.save(accountDetails);
		log.info("--->> Saved account details of " + user.getUsername());
		
		CreatedAccountDTO createdAccountDTO = CreatedAccountDTO.builder()
				.username(user.getUsername())
				.accountNumber(accountNumber)
				.accountType(accountDetails.getAccountType())
				.accountBalance(accountDetails.getBalance())
				.build();
		
		AccountCreationNotificationDTO accountCreationNotificationDTO = new AccountCreationNotificationDTO();
		accountCreationNotificationDTO.setNotificationType(SIGNUP);
		accountCreationNotificationDTO.setCreatedOn(new Date());
		accountCreationNotificationDTO.setFirstname(personalInfo.getFirstName());
		accountCreationNotificationDTO.setUsername(user.getUsername());
		accountCreationNotificationDTO.setInitialBalance(accountDetails.getBalance());
		accountCreationNotificationDTO.setAccountNumber(accountNumber);
		accountCreationNotificationDTO.setAccountType(accountDetails.getAccountType());

		notification.notifyUser(accountCreationNotificationDTO);
		
		return new ResponseDTO<>(SUCCESSFUL.getCode(), SUCCESSFUL.getMessage(), createdAccountDTO);
	}

	@Override
	public Optional<ContactInfo> createContactInfo(ContactInfoDTO contactInfoDTO, User user) {
		ContactInfo contactInfo = converterUtil.conv_ContactInfoDTO_ContactInfo(contactInfoDTO);
		contactInfo.setUser(user);
		
		return Optional.of(contactInfo);
	}

	@Override
	public Optional<PersonalInfo> createPersonalInfo(PersonalInfoDTO personalInfoDTO, User user) {
		PersonalInfo personalInfo = converterUtil.conv_PersonalInfoDTO_PersonalInfo(personalInfoDTO);
		personalInfo.setUser(user);
		
		return Optional.of(personalInfo);
	}

	@Override
	public Optional<Address> createAddressInfo(AddressDTO addressDTO, ContactInfo contactInfo) {
		Address address = converterUtil.conv_AddressDTO_Address(addressDTO);
		address.setContactInfo(contactInfo);
		
		return Optional.of(address);
	}

	@Override
	@Transactional
	public ResponseDTO<UpdatedAccountDTO> updateAccount(UpdateBalanceDTO updateBalanceDTO) {
		UpdatedAccountDTO updatedAccountDTO = new UpdatedAccountDTO();
		
		AccountDetails accountDetails = accountDetailsRepo.findByAccountNumber(updateBalanceDTO.getAccountNumber())
				.orElseThrow(() -> new AppException("Account not found exception"));
		accountDetails.setBalance(updateBalanceDTO.getAmount());
		accountDetailsRepo.save(accountDetails);
		
		updatedAccountDTO.setUsername(accountDetails.getUser().getUsername());
		updatedAccountDTO.setFirstName(accountDetails.getUser().getPersonalInfo().getFirstName());
				
		return new ResponseDTO<>(SUCCESSFUL.getCode(), SUCCESSFUL.getMessage(), updatedAccountDTO);
	}

	@Override
	public ResponseDTO<OauthDTO> login(LoginDetailsDTO loginDetailsDTO)
	{
		LoginNotificationDTO loginNotificationDTO = new LoginNotificationDTO();
		loginNotificationDTO.setNotificationType(LOGIN);
		
		log.info("---->>> Login process commences");
		User user = userRepo.findByUsername(loginDetailsDTO.getUsername())
				.orElseThrow(() -> new AppException("Username does not exist"));
		
		if(!encoder.matches(loginDetailsDTO.getPassword(), user.getPassword()))
		{
			throw new AppException("Password is incorrect");
		}
		
		if(!user.getIsActive())
		{
			throw new AppException("User has not been activated");
		}
		
		loginNotificationDTO.setCreatedOn(new Date());
		loginNotificationDTO.setFirstname(user.getPersonalInfo().getFirstName());
		loginNotificationDTO.setUsername(user.getUsername());
		loginNotificationDTO.setLocation(loginDetailsDTO.getLocation());

		notification.notifyUser(loginNotificationDTO);
		
		return new ResponseDTO<>(SUCCESSFUL.getCode(), SUCCESSFUL.getMessage(), 
				extCalls.generateAuthServeTokenPasswordGrantType(loginDetailsDTO.getUsername(), loginDetailsDTO.getPassword()));
	}
	
}