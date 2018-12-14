package com.zhongkouwei.blog.server.repository;

import com.zhongkouwei.blog.server.model.MongoTest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TestRepository extends MongoRepository<MongoTest,String> {
}
