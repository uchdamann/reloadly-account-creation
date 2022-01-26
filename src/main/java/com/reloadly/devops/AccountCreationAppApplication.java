package com.reloadly.devops;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@EnableAsync
@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Resource Server API for Reloadly Application", 
		version = "1.0", description = "Open API documentation"))
@SecurityScheme(name = "Authorization", scheme = "apiKey", type = SecuritySchemeType.APIKEY, in = SecuritySchemeIn.HEADER)
@SecurityScheme(name = "ChannelCode", scheme = "apiKey", type = SecuritySchemeType.APIKEY, in = SecuritySchemeIn.HEADER)
public class AccountCreationAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountCreationAppApplication.class, args);
	}

}
