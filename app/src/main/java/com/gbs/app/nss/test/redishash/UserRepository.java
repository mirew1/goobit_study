package com.gbs.app.nss.test.redishash;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<TestUser, String> {
//    List<TestUser> findByUserNm(String userNm);
}
