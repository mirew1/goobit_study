package com.gbs.app.nss.redis.redishash;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.gbs.app.nss.test.redishash.TestUser;
import com.gbs.app.nss.test.redishash.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;
    
    @Test
    @DisplayName("등록테스트")
    void saveUserInfo() throws Exception {
        TestUser testUser = new TestUser();
        testUser.setUsrNm("테스터1");
        testUser.setPasswd("1234");
        testUser.setUsrId("test1");
        testUser.setUsrMob("01011112222");
        TestUser save = userService.save(testUser);

        TestUser result = userService.findById(testUser.getUsrId());
        
        Assertions.assertThat(result.getUsrNm()).isEqualTo(save.getUsrNm());
    }
    
    @Test
    @DisplayName("@Indexed 복수개 테스트")
    void multIndexed() throws Exception {
        TestUser testUser = new TestUser();
        testUser.setUsrNm("테스터1");
        testUser.setPasswd("1234");
        testUser.setUsrId("test1");
        testUser.setUsrMob("01011112222");
        TestUser save = userService.save(testUser);

        TestUser testUser2 = new TestUser();
        testUser2.setUsrNm("테스터2");
        testUser2.setPasswd("1234");
        testUser2.setUsrId("test2");
        testUser2.setUsrMob("01022223333");
        TestUser save2 = userService.save(testUser2);
        
        TestUser result = userService.findByUsrNmAndUsrMob(testUser.getUsrNm(), testUser.getUsrMob());
        
        Assertions.assertThat(result.getUsrNm()).isEqualTo(save.getUsrNm());
        Assertions.assertThat(result.getUsrMob()).isEqualTo(save.getUsrMob());
        
    }
}