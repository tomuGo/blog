package com.zhongkouwei.blog.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Floor {

    /**
     * 楼层 1楼 2楼
     */
    private Integer floorId;

    private String content;

    private Integer userId;

    private String authorName;

    private Date createTime;
}
