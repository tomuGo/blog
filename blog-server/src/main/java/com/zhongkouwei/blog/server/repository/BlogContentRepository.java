package com.zhongkouwei.blog.server.repository;

import com.zhongkouwei.blog.server.model.BlogContent;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BlogContentRepository extends MongoRepository<BlogContent,String> {
}
