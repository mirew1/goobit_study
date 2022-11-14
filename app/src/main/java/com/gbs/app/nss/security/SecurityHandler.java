package com.gbs.app.nss.security;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gbs.app.nss.login.service.LoginService;

import lombok.extern.slf4j.Slf4j;

/**
 * 로그인 성공, 실패 핸들러
 */
@Component
@Slf4j
public class SecurityHandler implements AuthenticationFailureHandler, AuthenticationSuccessHandler, ApplicationListener<InteractiveAuthenticationSuccessEvent>{
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	ObjectMapper objectMapper;
	
	/**
	 * 로그인 실패 핸들러
	 */
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) throws IOException, ServletException {
		String target = "pw";
		
		if (authenticationException instanceof UsernameNotFoundException) {
			target = "id";
		}else if (authenticationException instanceof DisabledException) {
			target = "none";
		}else if (authenticationException instanceof CredentialsExpiredException) {
			target = "temp";
		}
		
		response.setStatus(200);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		objectMapper.writeValue(response.getWriter(), Map.of("isSuccess", false, "target", target));
		
	}
	
	/**
	 * 로그인 성공 핸들러
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

		response.setStatus(200);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		objectMapper.writeValue(response.getWriter(), Map.of("isSuccess", true));
		
	}
	
	/**
	 * 로그인 후 처리 (기존 성공 핸들러 사용 시 remember-me를 통한 로그인 이벤트가 발생하지 않음)
	 */
	@Override
	public void onApplicationEvent(InteractiveAuthenticationSuccessEvent event) {

		Authentication authentication = event.getAuthentication();
		String id = authentication.getName();
		
	}
}