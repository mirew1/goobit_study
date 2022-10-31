package com.gbs.app.nss.config;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheManager.RedisCacheManagerBuilder;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

import com.gbs.app.nss.redis.RedisCacheTTL;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableCaching
@RequiredArgsConstructor
public class RedisCacheConfig extends CachingConfigurerSupport {

	private final RedisConnectionFactory connectionFactory;
	
	/**
	 * Redis에 등록할 키값에 대한 생명주기 설정 
	 */
	@Bean
	@Override
	public CacheManager cacheManager() {
		RedisCacheManagerBuilder builder = RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(connectionFactory);
		Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
		
		// redis에 넣을 키값중 원하는 키에 대한 TTL 등 커스텀
		// cacheConfigurations key값 == @Cacheable의 value 값 
		cacheConfigurations.put(RedisCacheTTL.MENU.getLabel(), cacheConfiguration().entryTtl(Duration.ofSeconds(RedisCacheTTL.MENU.getSecond())));
		
		return builder.cacheDefaults(cacheConfiguration())
					  .withInitialCacheConfigurations(cacheConfigurations).build();
	}
	
    /**
     * 별도 생명 주기 미적용 시 적용될 Redis 기본 캐시 설정
     */
    @Bean
    public RedisCacheConfiguration cacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
        							  .entryTtl(Duration.ofSeconds(RedisCacheTTL.DEFAULT.getSecond()))
//        							  .disableCachingNullValues() // redis에 null은 캐시 안함
        							  .serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
        							  ;
    }
}
