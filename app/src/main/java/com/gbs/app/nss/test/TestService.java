package com.gbs.app.nss.test;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.gbs.app.nss.redis.RedisCacheTTL;

import lombok.extern.slf4j.Slf4j;

/**
 *	서비스단에서 사용 참고 
 */
@Slf4j
public class TestService {

	/**
	 * 메뉴 조회 > 메뉴 전체 조회 
	 */
	@Cacheable(value = RedisCacheTTL.CacheValue.MENU, keyGenerator = "customKeyGenerator")
	public String findMenuListAll() {
		return null;
	}

	/**
	 * validation 테스트 > 등록 처리 
	 */
	public int inputTempInfo() {
		return 0;
	}
	
	/**
	 * CUD 상황인 경우 redis 캐시 초기화 
	 */
	@CacheEvict(value = RedisCacheTTL.CacheValue.MENU, allEntries = true)
	public void whenCUDQueryExeute() {
		log.info("=========================");
		log.info("CUD 쿼리 실행 완료 가정");
		log.info("=========================");
	}
}
