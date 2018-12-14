package com.zhongkouwei.blog.server.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "m_blog_content")
@Data
public class BlogContent {

    @Id
    private String blogId;

    private List<Section> sections=new ArrayList<>();

    public void setSection(Section section){
        sections.add(section);
    }

}
