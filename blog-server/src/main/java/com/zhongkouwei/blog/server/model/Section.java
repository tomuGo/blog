package com.zhongkouwei.blog.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Section {

    private Integer userId;

    private Date createDate;

    private String content;

}
