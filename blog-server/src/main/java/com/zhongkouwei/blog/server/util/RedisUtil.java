package com.zhongkouwei.blog.server.util;

import com.zhongkouwei.blog.common.BlogConstants;
import com.zhongkouwei.blog.common.enums.BlogType;
import com.zhongkouwei.blog.common.model.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class RedisUtil {


    @Autowired
    private RedisTemplate redisTemplate;

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void saveBlog(String key, Object obj) {
        redisTemplate.opsForList().leftPush(key, obj);
    }

    public int generateFloorId(String key) {
        RedisAtomicInteger redisAtomicInteger = new RedisAtomicInteger(BlogConstants.FLOOR_ID + key, redisTemplate.getConnectionFactory());
        return redisAtomicInteger.addAndGet(1);
    }

    public int getFloorId(String key){
        RedisAtomicInteger redisAtomicInteger = new RedisAtomicInteger(BlogConstants.FLOOR_ID + key, redisTemplate.getConnectionFactory());
        return redisAtomicInteger.get();
    }



}
