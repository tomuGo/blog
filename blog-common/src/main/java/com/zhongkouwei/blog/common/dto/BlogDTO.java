package com.zhongkouwei.blog.common.dto;

import com.zhongkouwei.blog.common.group.BlogGroup;
import com.zhongkouwei.blog.common.model.Floor;
import lombok.Data;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
public class BlogDTO {

    @NotNull(message = "blogID不能为空",groups = BlogGroup.insertFloor.class)
    private String blogId;

    @NotNull(message = "博客名称不能为空",groups = BlogGroup.insertBlog.class)
    private String blogName;

    @NotNull(message = "博客类型不能为空",groups = BlogGroup.insertBlog.class)
    private Byte blogType;

    @NotNull(message = "作者ID不能为空",groups = {BlogGroup.insertBlog.class,BlogGroup.insertFloor.class})
    private Integer author;

    @NotNull(message = "作者不能为空",groups = {BlogGroup.insertBlog.class,BlogGroup.insertFloor.class})
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

    @NotNull(message = "博客1楼不能为空",groups = BlogGroup.insertBlog.class)
    private String floorOne;

    private List<Floor>floors;


}
