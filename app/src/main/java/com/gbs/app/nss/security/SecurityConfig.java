package com.gbs.app.nss.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import com.gbs.app.nss.login.service.LoginService;

/**
 * 
 *	Security WebSecurityConfigurerAdapter Deprecated 됨
 *	- 참고 : https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter
 * 	- 검색 키워드 : WebSecurityConfigurerAdapter Deprecated
 */
@EnableWebSecurity
public class SecurityConfig {
	
	/**
	 *  기존의 사용하던 해당 메서드 방식 대체
	 *  
	 *  @Override
	 *	public AuthenticationManager authenticationManagerBean() throws Exception {
	 *		return super.authenticationManagerBean();
	 *	}
	 * 
	 * 	- 옵션관련 내용
	 * 		- https://ziponia.github.io/2019/05/26/spring-security-authenticationmanager.html
	 */
//	@Bean
//	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//		return authenticationConfiguration.getAuthenticationManager();
//	}
	
	@Autowired
    private DataSource dataSource;
	
	@Autowired
	SecurityHandler securityHandler;
	
	@Autowired
	LoginService loginService;
	
	@Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.withUsername("user")
            .password("{noop}password")
            .roles("USER")
            .build();
        return new InMemoryUserDetailsManager(user);
    }
	
	
	@Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web
        					.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations())
        				.and()
        					.ignoring().mvcMatchers("/swagger-ui/**"); // 기본 static 하위 외 다른 경로 추가 시
    }
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
				.cors().disable()
				.csrf().disable()
				.authorizeRequests()
					.antMatchers("/board").hasRole("USER")
					.antMatchers("/").permitAll()
					.anyRequest().authenticated()
					
				// 로그인 설정
				.and()
					.formLogin()
						.loginPage("/lgn/form")
						.loginProcessingUrl("/lgn/lgn")
						.usernameParameter("usrId")
						.passwordParameter("passwd")
						.failureHandler(securityHandler)
						.successHandler(securityHandler)
				
				// 로그아웃 설정
				.and()
					.logout()
						.logoutUrl("/lgn/lgo")
						.logoutSuccessUrl("/lgn/form?lgo")
						.deleteCookies("JSESSIONID", "SPRING_SECURITY_REMEMBER_ME_COOKIE")
				
				// ajax exception handling
				.and()
			        .exceptionHandling()
					.authenticationEntryPoint(ajaxAuthenticationEntryPoint("/lgn/form"))
					
				// 자동로그인 설정
				.and()
					.rememberMe()
						.tokenValiditySeconds(86400 * 14) // 자동로그인 14일간 유지
						.userDetailsService(loginService) // 자동로그인 시 loadUserByUsername를 수행할 서비스 지정
						.tokenRepository(persistentTokenRepository())
						.rememberMeServices(rememberMeServices())
					
				.and()
					.sessionManagement()
						.maximumSessions(1) // 한 아이디당 로그인은 최대 1명
						.maxSessionsPreventsLogin(false) // false = 기존로그인 사용자 강제 로그아웃, true = 새 사용자 로그인 불가
						.expiredUrl("/lgn/form?denied=2") // 중복로그인으로 로그아웃 된 경우 기존 사용자가 이동될 url
				        .sessionRegistry(sessionRegistry())
				        
				.and()
				
			.and()
				.addFilterAfter(new CustomRememberMeExceptionFilter(), SecurityContextHolderAwareRequestFilter.class)
				.build();
	}
	
	private AjaxAuthenticationEntryPoint ajaxAuthenticationEntryPoint(String url) {
    	return new AjaxAuthenticationEntryPoint(url);
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
	
    @Bean
    public SecurityAuthProvider authProvider() {
    	return new SecurityAuthProvider(loginService, passwordEncoder());
    }
    
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        jdbcTokenRepository.setCreateTableOnStartup(true); // 리멤버미 테이블 생성 (한번 실행 수 false로 돌리자)
//        jdbcTokenRepository.setCreateTableOnStartup(false);
        return jdbcTokenRepository;
    }
    
    @Bean
    public RememberMeServices rememberMeServices() {
    	return new CustomRememberMeServices("myhomes", loginService);
    }
}
