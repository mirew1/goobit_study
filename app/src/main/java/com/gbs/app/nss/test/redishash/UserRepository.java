package com.gbs.app.nss.test.redishash;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 *	1) 메서드 파라미터로 사용되는 항목은 @Indexed 가 걸려있어야한다.
 * 	2) 메서드명, 파라미터명은 해당 객체의 property 이름 값과 동일해야한다.
 * 	3) 메서드 규칙은 JPA처럼 규칙이 있다. And, Or, Between 등.. 이건 검색해보는 걸로 
 */
public interface UserRepository extends CrudRepository<TestUser, String> {
    List<TestUser> findByUsrNm(String usrNm);
	Optional<TestUser> findByUsrNmAndUsrMob(String usrNm, String usrMob);
    
}
