package com.gbs.app.nss.redis.redistemplate;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * Redis에 데이터 입력 시 StringRedisTemplate 이용
 * 	- class 정보 제외하여 프론트쪽에 별도 프레임워크 사용하는 MSA서비스 대응(역직렬화 시 class 정보 없어도 되게)
 * 	- 별도 커스텀 안해도, 특정 Spring 버전 이상인 경우 RedisAutoConfiguration.java에 설정 잡혀있음
 * 
 * 참고
 * 	- https://blog.naver.com/PostView.nhn?blogId=cutesboy3&logNo=222285071695&categoryNo=22&parentCategoryNo=0&viewDate=&currentPage=1&postListTopCurrentPage=1&from=postView
 *  - https://mongsil-jeong.tistory.com/25
 *
 */
@Slf4j
@SpringBootTest
public class RedisTemplateTest {

	@Autowired
    private StringRedisTemplate stringRedisTemplate;
	
	@Autowired
	private ObjectMapper objectMapper;
	
    @Test
    @DisplayName("등록테스트")
    void saveUserInfo() throws Exception {
        TestUser testUser = new TestUser();
        testUser.setUsrNm("테스터1");
        testUser.setPasswd("1234");
        testUser.setUsrId("test1");
        testUser.setUsrMob("01011112222");
        
        putRedis("testUser", testUser);
        
        log.info("redis get :: {}", getRedisValue("testUser", TestUser.class).toString());
        
//        Assertions.assertThat(result.getUsrNm()).isEqualTo(save.getUsrNm());
    }
    
    public <T> T getRedisValue(String key, Class<T> classType) throws JsonProcessingException {
        String redisValue = (String) stringRedisTemplate.opsForValue().get(key);
        if (StringUtils.isEmpty(redisValue)) {
            return null;
        }else{
            return objectMapper.readValue(redisValue,classType);
        }
    }

    public void putRedis(String key,Object obj) throws JsonProcessingException {
    	stringRedisTemplate.opsForValue().set(key, objectMapper.writeValueAsString(obj));
    }
    
}
