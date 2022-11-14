package com.gbs.app.nss.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.gbs.app.nss.login.service.LoginService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 로그인 상세 처리를 위한 Provider
 */
@Slf4j
@RequiredArgsConstructor
public class SecurityAuthProvider implements AuthenticationProvider {

	private final LoginService loginService;
	private final PasswordEncoder encoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String loginId = authentication.getName();
		String password = (String) authentication.getCredentials();
		UserDetails result = loginService.loadUserByUsername(loginId);

		if (!result.isEnabled()) {
			throw new DisabledException("탈퇴된 회원입니다.");
		}
		
		if (!result.isCredentialsNonExpired()) {
			throw new CredentialsExpiredException("임시 비밀번호입니다.");
		}

		if (!encoder.matches(password, result.getPassword())) {
			throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
		}

		return new UsernamePasswordAuthenticationToken(result, result.getPassword(), result.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
