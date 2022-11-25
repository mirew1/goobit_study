package com.gbs.app.nss.redis.embed;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import com.gbs.app.nss.test.redishash.TestUser;
import com.gbs.app.nss.test.redishash.UserRepository;

/**
 *	@DataRedisTest 방식 사용
 *		- 실행 끝나고 뜨는 java.net.SocketException: Connection reset는 왜 뜨는지 모르겠,,음
 */

@DataRedisTest
@Import(EmbeddedRedisConfig.class)
@ActiveProfiles("test")
public class RedisEmbeddedTest {
	
	@Autowired
    private UserRepository userRepository;
	
	@BeforeEach
    void clear(){
		userRepository.deleteAll();
    }
	
	@DisplayName("저장테스트")
	@Test
	void save() {
		TestUser testUser = new TestUser();
        testUser.setUsrNm("테스터1");
        testUser.setPasswd("1234");
        testUser.setUsrId("test1");
        testUser.setUsrMob("01011112222");
        TestUser save = userRepository.save(testUser);

        TestUser result = userRepository.findById(testUser.getUsrId()).get();
        
        Assertions.assertThat(result.getUsrNm()).isEqualTo("테스터1");
	}
}
