package com.reloadly.devops.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Order(1)
@Component
public class AppFilter implements Filter {
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		if (!(request instanceof HttpServletRequest)) {
			chain.doFilter(request, response);
			return;
		}
		
		HttpServletRequest httpRequest = ((HttpServletRequest) request);

		String httpMethod = httpRequest.getMethod();
		String requestURI = httpRequest.getRequestURI();
		String sourceIp = httpRequest.getHeader("X-Forwarded-For") == null ? httpRequest.getRemoteAddr()
			: httpRequest.getHeader("X-Forwarded-For");
		
		log.info("---->>> HTTP METHOD: " + httpMethod);
		log.info("---->>> REQUEST URI: " + requestURI);
		log.info("---->>> SOURCE IP: " + sourceIp);
		
		chain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig) {}
	public void destroy() {}
}