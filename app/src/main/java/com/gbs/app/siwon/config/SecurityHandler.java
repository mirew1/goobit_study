package com.gbs.app.siwon.config;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.gbs.app.siwon.login.LoginService;

import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * 1. 패키지명 : com.myhomes.app.global.config
 * 2. 타입명 : SecurityHandler.java
 * 2. 작성일 : 2022. 1. 28. 오전 10:30:05
 * 3. 작성자 : siwon
 * 4. 설명 : 로그인 성공, 실패 핸들러
 * </pre>
 */
@Component
@Slf4j
public class SecurityHandler implements AuthenticationFailureHandler, AuthenticationSuccessHandler, ApplicationListener<InteractiveAuthenticationSuccessEvent>{
	
	@Autowired
	LoginService loginService;
	
	/**
	 * <pre>
	 * 1. 메소드명 : onAuthenticationFailure
	 * 2. 작성일 : 2022. 1. 27. 오후 4:38:42
	 * 3. 작성자 : siwon
	 * 4. 설명 : 로그인 실패 핸들러
	 * </pre>
	 * @param request
	 * @param response
	 * @param authenticationException
	 * @throws IOException
	 * @throws ServletException
	 */
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) throws IOException, ServletException {

		response.setStatus(200);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.write("{\"isSuccess\":false}");
		out.flush();
		
	}
	
	/**
	 * <pre>
	 * 1. 메소드명 : onAuthenticationSuccess
	 * 2. 작성일 : 2022. 1. 27. 오후 4:38:48
	 * 3. 작성자 : siwon
	 * 4. 설명 : 로그인 성공 핸들러
	 * </pre>
	 * @param request
	 * @param response
	 * @param authentication
	 * @throws IOException
	 * @throws ServletException
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

		response.setStatus(200);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.write("{\"isSuccess\":true}");
		out.flush();
		
	}
	
	/**
	 * <pre>
	 * 1. 메소드명 : onApplicationEvent
	 * 2. 작성일 : 2022. 2. 7. 오후 5:55:14
	 * 3. 작성자 : siwon
	 * 4. 설명 : 로그인 후 처리 (기존 성공 핸들러 사용 시 remember-me를 통한 로그인 이벤트가 발생하지 않음)
	 * </pre>
	 * @param event
	 */
	@Override
	public void onApplicationEvent(InteractiveAuthenticationSuccessEvent event) {

		Authentication authentication = event.getAuthentication();
		String id = authentication.getName();
		
	}
}