package com.gbs.app.nss.test.redishash;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public TestUser save(TestUser testUser) {
        return (TestUser) userRepository.save(testUser);
    }

    public List<TestUser> findByUsrNm(String usrNm) {
        return userRepository.findByUsrNm(usrNm);
    }
    
    public TestUser findById(String usrId) throws Exception {
    	return userRepository.findById(usrId).orElseThrow(Exception::new);
    }
    
    public TestUser findByUsrNmAndUsrMob(String usrNm, String usrMob) throws Exception {
    	return userRepository.findByUsrNmAndUsrMob(usrNm, usrMob).orElseThrow(Exception::new);
    }
}
