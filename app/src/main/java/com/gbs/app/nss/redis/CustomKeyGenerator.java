package com.gbs.app.nss.redis;

import java.lang.reflect.Method;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

/**
 *  Redis 키 생성 시 중복 방지용 커스텀
 *	 - Redis key값 중복 방지를 위한 생성 규칙 설정 ex.) 메서드명;파라미터1;파라미터2 ... (map이면 메서드명;{aa=bb, cc=dd ...};) 
 */
@Component
public class CustomKeyGenerator implements KeyGenerator {
	@Override
	public Object generate(Object target, Method method, Object... params) {  
		StringBuilder sb = new StringBuilder();
		sb.append(method.getName());
		
		if (params.length > 0) {
			sb.append(";");
			for (Object obj : params) {
				sb.append(obj);
				sb.append(";");
			}
		}
		
		return sb.toString();
	}
}
