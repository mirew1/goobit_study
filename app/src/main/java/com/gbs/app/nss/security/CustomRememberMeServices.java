package com.gbs.app.nss.security;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.security.web.authentication.rememberme.CookieTheftException;
import org.springframework.security.web.authentication.rememberme.InvalidCookieException;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationException;

import com.gbs.app.nss.login.mapper.LoginMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * remember-me 커스텀 클래스
 */
@Slf4j
public class CustomRememberMeServices extends AbstractRememberMeServices {
	
	protected CustomRememberMeServices(String key, UserDetailsService userDetailsService) {
		super(key, userDetailsService);
		this.random = new SecureRandom();
	}

	private SecureRandom random;

	public static final int DEFAULT_SERIES_LENGTH = 16;
	public static final int DEFAULT_TOKEN_LENGTH = 16;

	private int seriesLength = DEFAULT_SERIES_LENGTH;
	private int tokenLength = DEFAULT_TOKEN_LENGTH;
	
	@Autowired
	private PersistentTokenRepository tokenRepository;
	
	@Autowired
	private LoginMapper loginMapper;

	
	/**
	 * 로그인 성공 시 리멤버미 DB적재 
	 */
	@Override
	protected void onLoginSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication successfulAuthentication) {
		String username = successfulAuthentication.getName();
		PersistentRememberMeToken persistentToken = new PersistentRememberMeToken(username, generateSeriesData(),
				generateTokenData(), new Date());
		try {
			// 타 기기 접속 기록 삭제 + 현재 접근 기기의 기존 리멤버미 쿠키 삭제
			cancelCookie(request, response);
			tokenRepository.removeUserTokens(username);
			
			tokenRepository.createNewToken(persistentToken);
			addCookie(persistentToken, request, response);
		}
		catch (Exception ex) {
			log.error("토큰 저장 실패", ex);
		}
	}
	
	
	/**
	 * 로그아웃 시 해당 유저의 모든 리멤버미 정보 삭제
	 * 		- 중복 로그인 방지 세션 정책과 흐름 일치
	 */
	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		super.logout(request, response, authentication);
		boolean isLogout = request.getRequestURI().contains("lgo");
		
		// 로그아웃인 경우에만 해당 유저의 토큰 삭제
		// denied인 경우에 삭제 안되게
		if (isLogout && authentication != null) {
			tokenRepository.removeUserTokens(authentication.getName());
		}
	}
	
	/**
	 * 접속 시 쿠키 체크 후 자동 로그인 수행 (JSESSIONID 없는 경우 동작)
	 * 		- return 객체를 이용해 check 메서드 수행하기 때문에 NULL 반환 시 NullPointerException 
	 */
	@Override
	protected UserDetails processAutoLoginCookie(String[] cookieTokens, HttpServletRequest request,
			HttpServletResponse response) throws RememberMeAuthenticationException, UsernameNotFoundException {
		// 쿠키 : series, token 두개가 있어야 한다
		if (cookieTokens.length != 2) {
			throw new InvalidCookieException("잘못된 쿠키입니다.");
		}
		
		String presentedSeries = cookieTokens[0]; // 쿠키를 구별하는 고유값
		String presentedToken = cookieTokens[1]; // DB데이터와 비교할 쿠키값
		
		log.debug("presentedToken : {}", presentedToken);
		
		PersistentRememberMeToken token = tokenRepository.getTokenForSeries(presentedSeries);
		
		// DB에 series에 맞는 쿠키가 없는 경우 : 인증 불가
		if (token == null) {
			throw new RememberMeAuthenticationException("series id에 일치하는 쿠키가 없습니다. series id: " + presentedSeries);
		}
		
		// 로그인하려는 토큰과 DB의 토큰이 일치하지 않는 경우
		if (!presentedToken.equals(token.getTokenValue())) {
			loginMapper.removeUserTokensBySeries(presentedSeries);
			throw new CookieTheftException("로그인에 이용한 토큰이 DB와 일치하지 않습니다.");
		}
		
		// 유효시간 넘은 경우
		if (token.getDate().getTime() + getTokenValiditySeconds() * 1000L < System.currentTimeMillis()) {
			// DB에서 DELETE
			loginMapper.removeUserTokensBySeries(token.getSeries());
			throw new RememberMeAuthenticationException("유효시간이 만료된 토큰입니다.");
		}
		
		// 일치하는 경우
		PersistentRememberMeToken newToken = new PersistentRememberMeToken(token.getUsername(), token.getSeries(),
				generateTokenData(), new Date());
		try {
			tokenRepository.updateToken(newToken.getSeries(), newToken.getTokenValue(), newToken.getDate()); // 토큰갱신 + 유효기간 연장
			addCookie(newToken, request, response);
		}
		catch (Exception ex) {
			log.error("토큰 갱신 실패", ex);
			throw new RememberMeAuthenticationException("토큰 갱신 중 오류 발생");
		}
		
		return getUserDetailsService().loadUserByUsername(token.getUsername());
	}
	
	protected String generateTokenData() {
		byte[] newToken = new byte[this.tokenLength];
		this.random.nextBytes(newToken);
		return new String(Base64.getEncoder().encode(newToken));
	}
	
	protected String generateSeriesData() {
		byte[] newSeries = new byte[this.seriesLength];
		this.random.nextBytes(newSeries);
		return new String(Base64.getEncoder().encode(newSeries));
	}
	
	private void addCookie(PersistentRememberMeToken token, HttpServletRequest request, HttpServletResponse response) {
		setCookie(new String[] { token.getSeries(), token.getTokenValue() }, getTokenValiditySeconds(), request,
				response);
	}
}
