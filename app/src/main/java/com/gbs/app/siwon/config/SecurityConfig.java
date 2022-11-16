package com.gbs.app.siwon.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import com.gbs.app.siwon.login.LoginService;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	@Autowired
	SecurityHandler securityHandler;

	@Autowired
	LoginService loginService;

    // security 인증 절차 제외 (static 하위 정적자원)
    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring().mvcMatchers("/font/**", "/css/**", "/js/**", "/img/**", "/themes/**");
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http.cors().disable()
        	.csrf().disable()
        	.headers().frameOptions().sameOrigin()
        	
    	.and()
    	.authorizeRequests()
			.antMatchers("/login*").permitAll()			// 로그인 페이지와, 회원가입등 페이지는 제한없음
			.antMatchers("/**").hasAnyRole("USER")		// 그외 모든 페이지 로그인 사용자만 접근가능

		// 로그인 설정
		.and()
		.formLogin()
			.loginPage("/login")
			.loginProcessingUrl("/login/login")
			.usernameParameter("userId")
			.passwordParameter("password")
			.failureHandler(securityHandler)
			.successHandler(securityHandler)


		// 로그아웃 설정
		.and()
		.logout()
			.logoutUrl("/login/logout")
			.logoutSuccessUrl("/login?logout")
			.deleteCookies("JSESSIONID", "SPRING_SECURITY_REMEMBER_ME_COOKIE")

		// ajax exception handling
		.and()
	        .exceptionHandling()
			.authenticationEntryPoint(ajaxAuthenticationEntryPoint("/login"))

		// 자동로그인 설정
		.and()
		.rememberMe()
			.tokenValiditySeconds(86400 * 7) // 자동로그인 7일간 유지
			.userDetailsService(loginService) // 자동로그인 시 loadUserByUsername를 수행할 서비스 지정

		.and()
		.sessionManagement()
			.maximumSessions(1) // 한 아이디당 로그인은 최대 1명
			.maxSessionsPreventsLogin(false) // false = 기존로그인 사용자 강제 로그아웃, true = 새 사용자 로그인 불가
			.expiredUrl("/login?denied=2") // 중복로그인으로 로그아웃 된 경우 기존 사용자가 이동될 url
	        .sessionRegistry(sessionRegistry())
	        
	    .and().and().build();
	}

	/**
	 * 비밀번호 인코더 (Bcrypt 인코딩)
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
    protected SessionRegistryImpl sessionRegistry(){
        return new SessionRegistryImpl();
    }

	@Bean
	public static ServletListenerRegistrationBean httpSessionEventPublisher() {
		return new ServletListenerRegistrationBean(new HttpSessionEventPublisher());
	}

    private AjaxAuthenticationEntryPoint ajaxAuthenticationEntryPoint(String url) {
    	return new AjaxAuthenticationEntryPoint(url);
    }


}
