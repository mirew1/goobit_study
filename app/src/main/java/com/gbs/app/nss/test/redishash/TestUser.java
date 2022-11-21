package com.gbs.app.nss.test.redishash;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@RedisHash(value = "MHS_USR", timeToLive = 300)
public class TestUser {

    @Id
    private String usrId;
    private String passwd;
    
    @Indexed
    private String usrNm;
    
    @Indexed
    private String usrMob;

}
