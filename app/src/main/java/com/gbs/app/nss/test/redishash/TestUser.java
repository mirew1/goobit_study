package com.gbs.app.nss.test.redishash;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@RedisHash("MHS_USR")
public class TestUser {

    @Id
    private String usrId;
    private String passwd;
    private String usrNm;
    private String usrMob;

}
