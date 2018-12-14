package com.zhongkouwei.blog.server.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "test")
@Data
@NoArgsConstructor
public class MongoTest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private Integer age;




}
