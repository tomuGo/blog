package com.zhongkouwei.blog.server.repository;

import com.zhongkouwei.blog.common.model.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BlogRepository extends MongoRepository<Blog,String> {

    Page<Blog> findByAuthorAndBlogTypeAndBlogNameIsLike(
            @Param("userId") Integer userId, @Param("blogType") String blogType, @Param("blogName") String blogName, Pageable pageable);

    List<Blog> findByBlogId(String blogId);
}
