package com.zhongkouwei.blog.common.dto;

import com.zhongkouwei.blog.common.model.Floor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BlogDTO {

    private String blogId;

    private String blogName;

    private Byte blogType;

    private Integer author;

    private String authorName;

    private Date createTime;

    private Date lastModifeTime;

    /**
     * 楼层数量
     */
    private Integer floorNum;

    /**
     *  1 加精   0：非
     */
    private Byte boutique;

    private List<Floor> floors;


}
