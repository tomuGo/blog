package com.zhongkouwei.blog.server.service;


import com.zhongkouwei.blog.common.dto.BlogDTO;
import com.zhongkouwei.blog.common.group.BlogGroup;
import com.zhongkouwei.blog.common.model.Blog;
import com.zhongkouwei.blog.common.model.BlogContent;
import com.zhongkouwei.blog.common.model.Floor;
import com.zhongkouwei.blog.common.model.PageInfo;
import com.zhongkouwei.blog.server.repository.BlogContentRepository;
import com.zhongkouwei.blog.server.repository.BlogRepository;
import com.zhongkouwei.blog.server.util.RedisLock;
import com.zhongkouwei.blog.server.util.RedisUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Service
public class BlogService {

    @Autowired
    BlogContentRepository blogContentRepository;
    @Autowired
    BlogRepository blogRepository;
    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    ValidatorService validatorService;
    @Autowired
    RedisLock redisLock;

    @Transactional(rollbackFor = Exception.class)
    public String addBlog(BlogDTO blogDTO) {
        validatorService.validate(blogDTO, BlogGroup.insertBlog.class);

        Blog blog = new Blog();
        Date createTime = new Date();
        BeanUtils.copyProperties(blogDTO, blog);
        blog.setCreateTime(createTime);
        blog.setBlogNums(1);
        Blog blogSaved = blogRepository.save(blog);

        Floor floor = new Floor();
        floor.setCreateTime(createTime);
        int floorId = redisUtil.generateFloorId(blogSaved.getBlogId());
        floor.setFloorId(floorId);
        floor.setAuthorName(blogDTO.getAuthorName());
        floor.setUserId(blogDTO.getAuthor());
        floor.setContent(blogDTO.getFloorOne());
        BlogContent blogContent = new BlogContent();
        blogContent.setBlogId(blogSaved.getBlogId());
        blogContent.setFloor(floor);
        blogContentRepository.save(blogContent);

        return blogSaved.getBlogId();
    }

    public Integer addSection(Floor section, String blogId) {
        BlogContent blogContent = blogContentRepository.findOne(blogId);

        int floorId = redisUtil.generateFloorId(blogId);
        section.setCreateTime(new Date());
        section.setFloorId(floorId);
        blogContent.setFloor(section);
        blogContentRepository.save(blogContent);

        Thread updateBlog = new Thread(new Runnable() {
            @Override
            public void run() {
                tryBlogIDLock(blogId);
            }
        });
        updateBlog.start();

        return floorId;

    }

    public PageInfo<Blog> getBlogs(Pageable pageable, Criteria criteria) {
        Query query = new Query(criteria);
        query.with(pageable);
        List<Blog> blogList = mongoTemplate.find(query, Blog.class);
        Long count = mongoTemplate.count(query, Blog.class);
        PageInfo<Blog> blogPageInfo = new PageInfo<>(blogList, pageable.getPageNumber() + 1, pageable.getPageSize(), count);
        return blogPageInfo;
    }

    public BlogDTO getBlogDto(String blogId) {
        BlogContent blogContent = blogContentRepository.findOne(blogId);
        Blog blog = blogRepository.findOne(blogId);
        BlogDTO blogDTO = new BlogDTO();
        if (blogContent != null) {
            blogDTO.setFloors(blogContent.getFloors());
        }
        if (blog != null) {
            BeanUtils.copyProperties(blog, blogDTO);
        }
        return blogDTO;

    }

    private void tryBlogIDLock(String blogId) {
        String uuid = redisLock.lock(blogId);
        int times = 0;
        if (!StringUtils.isEmpty(uuid)) {
            Blog blog = blogRepository.findOne(blogId);
            if (blog != null) {
                Integer sections = blog.getBlogNums() + 1;
                blog.setBlogNums(sections);
                blog.setLastModifeTime(new Date());
                blogRepository.save(blog);
            }
            //开锁
            redisLock.unlock(blogId, uuid);
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            times++;
            if (times < 20) {
                tryBlogIDLock(blogId);
            }
        }
    }


}
