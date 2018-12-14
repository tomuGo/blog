package com.zhongkouwei.blog.server.dto;

import com.zhongkouwei.blog.server.model.Blog;
import com.zhongkouwei.blog.server.model.BlogContent;
import lombok.Data;

@Data
public class BlogDTO {

    private BlogContent blogContent;

    private Blog blog;

}
