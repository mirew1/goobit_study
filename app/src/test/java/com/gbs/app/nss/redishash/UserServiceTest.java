package com.gbs.app.nss.redishash;

import com.gbs.app.nss.test.redishash.TestUser;
import com.gbs.app.nss.test.redishash.UserRepository;
import com.gbs.app.nss.test.redishash.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("등록테스트")
    void saveUserInfo() {
        TestUser testUser = new TestUser();
        testUser.setUsrNm("테스터1");
        testUser.setPasswd("1234");
        testUser.setUsrId("test1");
        testUser.setUsrMob("01011112222");
        TestUser save = userRepository.save(testUser);

        TestUser result = userRepository.findById("test1").get();

        Assertions.assertEquals(result.getUsrId(), "test1");
    }
}