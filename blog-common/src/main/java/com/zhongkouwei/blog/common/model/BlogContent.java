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

    private List<Floor> floors;

    public void setFloor(Floor section) {
        if (floors == null || floors.size() == 0) {
            floors = new ArrayList<>();
        }
        floors.add(section);
    }

}
