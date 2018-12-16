package com.zhongkouwei.blog.server.util;

import com.zhongkouwei.blog.common.enums.BlogType;
import com.zhongkouwei.blog.common.model.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class RedisUtil {


    @Autowired
    private RedisTemplate redisTemplate;

    public Object get(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public void saveBlog(String key,Object obj){
        redisTemplate.opsForList().leftPush(key,obj);
    }

    public List<Blog> getBlogList(Byte blogType, Integer pageNumber, Integer pageSize){
        String key=BlogType.getBlogKey(blogType);
        List<Blog> list=redisTemplate.opsForList().range(key,pageSize*(pageNumber-1),pageSize*(pageNumber)-1);
        return list;
    }

}
