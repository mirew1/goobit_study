package com.gbs.app.nss.redis;

import lombok.AllArgsConstructor;
import lombok.Getter;

// Redis 수명설정, @Cacheable 변수 정의
@Getter
@AllArgsConstructor
public enum RedisCacheTTL {
	DEFAULT(60, CacheValue.DEFAULT), MENU(120, CacheValue.MENU);
	
	private int second;
	private String label;
	
	// @Cacheable의 value 값으로 이용
	public class CacheValue {
		public static final String DEFAULT = "default";
		public static final String MENU = "menu";
	}
}
