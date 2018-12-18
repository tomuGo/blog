package com.zhongkouwei.blog.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PageInfo<T> {

    private List<T> list;

    private Integer pageNumber;

    private Integer pageSize;

    private long count;
}
