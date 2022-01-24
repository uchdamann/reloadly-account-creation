package com.reloadly.devops.utilities;

import java.util.Arrays;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.reloadly.devops.exceptions.AppException;
import com.reloadly.devops.response.dtos.OauthDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ExternalCalls {

	@Autowired
	private AppProperties props;
	@Autowired
	private RestTemplate restTemplate;

	public OauthDTO generateAuthServeTokenPasswordGrantType(final String username, final String password) {
		OauthDTO oauthDTO = null;
		String basicAuth = props.getClientId() + ":" + props.getClientSecret();

		HttpHeaders requestHeader = new HttpHeaders();
		requestHeader.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		requestHeader.add("Authorization", "Basic " + Base64.getEncoder().encodeToString(basicAuth.getBytes()));

		log.info("---->>> Initiating process to get oauth token from auth-server");

		MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();

		requestBody.put("grant_type", Arrays.asList(props.getGrantTypePassword()));
		requestBody.put("username", Arrays.asList(username));
		requestBody.put("password", Arrays.asList(password));

		log.info("Auth-server request: {}", requestBody);

		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, requestHeader);
		ResponseEntity<String> response = restTemplate.postForEntity(props.getAuthServerUrl(), requestEntity,
				String.class);

		if (response != null) {
			oauthDTO = JsonBuilder.toClass(response.getBody(), OauthDTO.class);
			log.info("---->>> OauthDTO: {}", oauthDTO);
		} else {
			log.info("---->>> No Response from authorization server");
			throw new AppException("---->>> No Response from authorization server");
		}

		return oauthDTO;
	}

//	public OauthDTO generateAuthServeTokenClientCredentialsGrantType() {
//		OauthDTO oauthDTO = null;
//		String basicAuth = props.getClientId() + ":" + props.getClientSecret();
//
//		HttpHeaders requestHeader = new HttpHeaders();
//		requestHeader.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//		requestHeader.add("Authorization", "Basic " + Base64.getEncoder().encodeToString(basicAuth.getBytes()));
//
//		log.info("---->>> Initiating process to get oauth token from auth-server");
//
//		MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
//
//		requestBody.put("grant_type", Arrays.asList(props.getGrantTypeClientCredentials()));
//
//		log.info("Auth-server request: {}", requestBody);
//
//		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, requestHeader);
//		ResponseEntity<String> response = restTemplate.postForEntity(props.getAuthServerUrl(), requestEntity,
//				String.class);
//
//		if (response != null) {
//			oauthDTO = JsonBuilder.toClass(response.getBody(), OauthDTO.class);
//			log.info("---->>> OauthDTO: {}", oauthDTO);
//		} else {
//			log.info("---->>> No Response from authorization server");
//			throw new AppException("---->>> No Response from authorization server");
//		}
//
//		return oauthDTO;
//	}

//	public EscrowPaymentRequestDTO escrowCredit(EscrowPaymentRequestDTO escrowPaymentRequestDTO) {
//		log.info("---->>> Initiating process to credit escrow, {}", escrowPaymentRequestDTO);
//
//		HttpHeaders requestHeader = new HttpHeaders();
//		EscrowPaymentRequestDTO escrowPaymentRequestDTO1 = null;
//		final String escrowCreditURL = props.getEscrowEndpoint() + "credit";
//
//		ResponseDTO<EscrowPaymentRequestDTO> responseDTO = null;
//
//		requestHeader.setContentType(MediaType.APPLICATION_JSON);
//		requestHeader.add("Authorization",
//				"Bearer " + generateAuthServeTokenClientCredentialsGrantType().getAccessToken());
//		requestHeader.add("ChannelCode", props.getWebChannelCode());
//
//		HttpEntity<EscrowPaymentRequestDTO> requestEntity = new HttpEntity<>(escrowPaymentRequestDTO, requestHeader);
//		ResponseEntity<String> response = restTemplate.postForEntity(escrowCreditURL, requestEntity, String.class);
//
//		if (response != null) {
//			responseDTO = JsonBuilder.toClassTypeReference(response.getBody(),
//					new TypeReference<ResponseDTO<EscrowPaymentRequestDTO>>() {
//					});
//			escrowPaymentRequestDTO1 = responseDTO.getData();
//
//		} else {
//			log.info("---->>> No Response from escrow server");
//			throw new AppException("---->>> No Response from escrow server");
//		}
//
//		return escrowPaymentRequestDTO1;
//	}

//	public EscrowPaymentRequestDTO escrowDebit(String transactionReference) {
//		log.info("---->>> Initiating process to debit escrow. Transaction reference: {}", transactionReference);
//
//		HttpHeaders requestHeader = new HttpHeaders();
//		EscrowPaymentRequestDTO escrowPaymentRequestDTO = null;
//		final String escrowCreditURL = props.getEscrowEndpoint() + "debit/" + transactionReference;
//
//		ResponseDTO<EscrowPaymentRequestDTO> responseDTO = null;
//
//		requestHeader.setContentType(MediaType.APPLICATION_JSON);
//		requestHeader.add("Authorization",
//				"Bearer " + generateAuthServeTokenClientCredentialsGrantType().getAccessToken());
//		requestHeader.add("ChannelCode", props.getWebChannelCode());
//
//		HttpEntity<EscrowPaymentRequestDTO> requestEntity = new HttpEntity<>(null, requestHeader);

		/*
		 * Rest template handles both client and server error responses by throwing it,
		 * so, it's best you surround your rest template request in a try catch block,
		 * in order to catch the errors..
		 */
		
//		try {
//			ResponseEntity<String> response = restTemplate.exchange(escrowCreditURL, HttpMethod.GET, requestEntity,
//					String.class);
//
//			if (response != null) {
//				responseDTO = JsonBuilder.toClassTypeReference(response.getBody(),
//						new TypeReference<ResponseDTO<EscrowPaymentRequestDTO>>() {});
//				escrowPaymentRequestDTO = responseDTO.getData();
//			} else {
//				log.info("---->>> No Response from escrow server");
//				throw new AppException("---->>> No Response from escrow server");
//			}
//		} catch (HttpClientErrorException | HttpServerErrorException ex) {
//			log.error("---->>> ERROR");
//		}
//
//		return escrowPaymentRequestDTO;
//	}
}