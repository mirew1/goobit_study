package com.gbs.app.nss.login.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.security.core.userdetails.UserDetails;

@Mapper
public interface LoginMapper {

	UserDetails selectUser(String username);

	int removeUserTokensBySeries(String presentedSeries);

}
