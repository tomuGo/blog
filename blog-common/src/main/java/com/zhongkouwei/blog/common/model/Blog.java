package com.zhongkouwei.blog.common.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Document(collection = "m_blog")
@Data
public class Blog implements Serializable {

    private static final long serialVersionUID = -8007968219168855917L;

    @Id
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
    private Integer blogNums;

    /**
     *  1 加精   0：非
     */
    private Byte boutique;

}
