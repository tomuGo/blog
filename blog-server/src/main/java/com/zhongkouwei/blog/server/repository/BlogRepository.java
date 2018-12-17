package com.zhongkouwei.blog.server.repository;

import com.zhongkouwei.blog.common.model.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BlogRepository extends MongoRepository<Blog,String> {

    Page<Blog> findByAuthorAndBlogTypeAndBlogNameIsLikeAndBoutique(
            @Param("userId") Integer userId, @Param("blogType") Byte blogType, @Param("blogName") String blogName,@Param("boutique")Byte boutique, Pageable pageable);

    List<Blog> findByBlogId(String blogId);
}
