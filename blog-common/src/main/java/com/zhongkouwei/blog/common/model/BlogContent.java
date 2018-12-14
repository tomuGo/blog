package com.zhongkouwei.blog.common.model;

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

    private List<Floor> sections;

    public void setSection(Floor section) {
        if (sections == null || sections.size() == 0) {
            sections = new ArrayList<>();
        }
        sections.add(section);
    }

}
