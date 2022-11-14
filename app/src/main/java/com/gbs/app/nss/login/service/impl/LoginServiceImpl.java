package com.gbs.app.nss.login.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gbs.app.nss.login.mapper.LoginMapper;
import com.gbs.app.nss.login.service.LoginService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

	private final LoginMapper loginMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails user = loginMapper.selectUser(username);
//    	user정보가 null로 전달되는경우 에러가 발생하여 임시처리z
    	if(user == null) {
    		throw new UsernameNotFoundException("해당 ID로 가입된 회원이 없습니다. :" +username);
    	}
        return user;
	}

}
