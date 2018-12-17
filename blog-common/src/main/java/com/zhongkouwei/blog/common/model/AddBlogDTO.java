package com.zhongkouwei.blog.common.model;

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
