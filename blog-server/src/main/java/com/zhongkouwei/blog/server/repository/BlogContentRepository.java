package com.zhongkouwei.blog.server.repository;

import com.zhongkouwei.blog.common.model.BlogContent;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BlogContentRepository extends MongoRepository<BlogContent,String> {
}
