package com.gbs.app.nss.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.authentication.rememberme.CookieTheftException;
import org.springframework.web.filter.GenericFilterBean;

import lombok.extern.slf4j.Slf4j;

/**
 * Spring Security remember-me 관련 별도 처리
 * 
 */
@Slf4j
public class CustomRememberMeExceptionFilter extends GenericFilterBean {
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse res = (HttpServletResponse) response;
		
		try {
			chain.doFilter(request, response);
		} catch (CookieTheftException e) {
			log.error("remember-me 값에 문제 발생", e);
			res.sendRedirect("/lgn/form");
		}
	}

}
