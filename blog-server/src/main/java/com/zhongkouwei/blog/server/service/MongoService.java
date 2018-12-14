package com.zhongkouwei.blog.server.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.util.StringUtils;

/**
* @Author wangyiming1031@aliyun.com 
* @Description 
* @Date 2018/11/9 11:08
**/
public class MongoService {

    public static Criteria convertCriteria(String blogName, Byte blogType, Integer author,Byte boutique) {
        Criteria criteria=new Criteria();
        if(!StringUtils.isEmpty(blogName)){
            criteria.and("blogName").regex(blogName);
        }
        if(!StringUtils.isEmpty(blogType)){
            criteria.and("blogType").is(blogType);
        }
        if(null!=author){
            criteria.and("author").is(author);
        }
        if(null!=boutique){
            criteria.and("boutique").in(boutique);
        }
        return criteria;
    }

    public static Pageable convertPageable(Integer pageNumber,Integer pageSize,String orderName,String orderType){
        if(StringUtils.isEmpty(orderType)){
            orderType=" DESC ";
        }
        if(StringUtils.isEmpty(orderName)){
            orderName=" blog_id ";
        }
        Sort sort = new Sort(orderType, orderName);
        Pageable pageable = new PageRequest(pageNumber, pageSize,sort);
        return pageable;
    }
}
