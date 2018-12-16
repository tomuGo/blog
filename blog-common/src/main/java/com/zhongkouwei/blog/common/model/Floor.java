package com.zhongkouwei.blog.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Floor {

    private String content;

    private Integer userId;

    private String authorName;

    private Date createTime;
}
