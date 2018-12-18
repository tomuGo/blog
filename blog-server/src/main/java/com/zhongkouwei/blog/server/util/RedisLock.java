package com.zhongkouwei.blog.server.util;

import com.zhongkouwei.blog.common.BlogConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import redis.clients.jedis.Jedis;

import java.util.UUID;

@Component
public class RedisLock {

    private static final long timeOut = 60L;

    /**
     * NX|XX, NX -- Only set the key if it does not already exist. XX -- Only set the key
     *              if it already exist
     */
    private static final String SET_IF_NOT_EXIXT = "NX";

    /**
     * EX|PX, expire time units: EX = seconds; PX = milliseconds
     */
    private static final String UNIT = "EX";

    private static final String IS_OK = "OK";

    @Autowired
    Jedis jedis;

    public String lock(String key) {
        Assert.notNull(key,"无法上锁，没有key");
        String uuid = UUID.randomUUID().toString();
        String lockKey = BlogConstants.REDIS_LOCK + key;
        String isOK=jedis.set(lockKey, uuid, SET_IF_NOT_EXIXT,UNIT,timeOut);
        System.out.println(isOK);
        if(IS_OK.equals(isOK)){
            return uuid;
        }
        return null;
    }

    public Boolean unlock(String key, String uuid) {
        Assert.notNull(key,"无法解锁,没有key");
        Assert.notNull(uuid,"无法解锁,没有value");
        String lockKey = BlogConstants.REDIS_LOCK + key;
        String value=jedis.get(lockKey);
        if(uuid.equals(value)){
            jedis.del(lockKey);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

}
