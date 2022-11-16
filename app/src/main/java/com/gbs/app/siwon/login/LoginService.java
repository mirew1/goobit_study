package com.gbs.app.siwon.login;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * 1. 패키지명 : com.myhomes.app.lgn.service
 * 2. 타입명 : CustomUserDetailsService.java
 * 2. 작성일 : 2022. 1. 27. 오후 5:44:15
 * 3. 작성자 : siwon
 * 4. 설명 : security의 userDetailService를 상속받아 로그인기능 구현
 *          그외 세션삭제 기능 추가
 * </pre>
 */
@Service
@Slf4j
public class LoginService implements UserDetailsService {

	/**
	 * <pre>
	 * 1. 메소드명 : loadUserByUsername
	 * 2. 작성일 : 2022. 1. 27. 오후 5:45:18
	 * 3. 작성자 : siwon
	 * 4. 설명 : security에서 사용하는 사용자 정보 조회 메서드
	 * </pre>
	 * @param username
	 * @return
	 * @throws UsernameNotFoundException
	 */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	// DB에서 계정 가져갔다고 치고
    	UserDTO user = new UserDTO();
    	user.setUserId(username);
    	user.setUserNm("김철수");
    	user.setPassword(new BCryptPasswordEncoder().encode("1234"));
    	user.setAuthCd("ROLE_USER");
//    	user정보가 null로 전달되는경우 에러가 발생하여 임시처리
    	if(user == null) {
    		throw new UsernameNotFoundException("User not exist with name :" +username);
    	}
        return user;
    }
    
}
