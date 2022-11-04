package com.gbs.app.nss.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 
 *	Security WebSecurityConfigurerAdapter Deprecated 됨
 *	- 참고 : https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter
 * 	- 검색 키워드 : WebSecurityConfigurerAdapter Deprecated
 */
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
    UserDetailsService userDetailsService;
	
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
		
		.and().build();
	}
	
}
