package com.reloadly.devops.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.io.IOUtils;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter
{
	@Override
    public void configure(HttpSecurity http) throws Exception
    {
		http.headers().frameOptions().sameOrigin();
    	
    	http
    		.csrf().disable()
    		.authorizeRequests()
			.antMatchers("/", "/index", "/v3/api-docs/**", "/swagger-resources/**", "/swagger-ui/**",
				"/swagger-ui.html","/webjars/**","/resources/**", "/api/v1/**").permitAll()
			.anyRequest().authenticated()
			
			.and().sessionManagement()
	        .sessionCreationPolicy(SessionCreationPolicy.NEVER);
    }
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception 
	{
		resources.tokenServices(tokenServices());
	}

	@Bean
	@Primary
	public DefaultTokenServices tokenServices() throws Exception
	{
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
		return defaultTokenServices;
	}
	
	@Bean
	public TokenStore tokenStore() throws Exception
	{
		return new JwtTokenStore(accessTokenConverter());
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() throws Exception
	{
		String publicKey = null;
		JwtAccessTokenConverter converter = null;
		Resource publicResource = new ClassPathResource("publicKey.txt");

		try
		{
			converter = new JwtAccessTokenConverter();
			publicKey = IOUtils.toString(new InputStreamReader(publicResource.getInputStream()));
			converter.setVerifierKey(publicKey);
		} 
		catch (final IOException e)
		{
			log.info(e.getMessage());
		}
		
		return converter;
	}
}