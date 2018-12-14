package com.zhongkouwei.blog.server.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AddBlogDTO {

    @NotNull
    private String blogName;

    @NotNull
    private Byte blogType;

    @NotNull
    private String content;
}
