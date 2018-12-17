package com.zhongkouwei.blog.server.service;


import com.zhongkouwei.blog.common.dto.AddBlogDTO;
import com.zhongkouwei.blog.common.dto.BlogDTO;
import com.zhongkouwei.blog.common.enums.BlogType;
import com.zhongkouwei.blog.common.group.BlogGroup;
import com.zhongkouwei.blog.common.model.Blog;
import com.zhongkouwei.blog.common.model.BlogContent;
import com.zhongkouwei.blog.common.model.Floor;
import com.zhongkouwei.blog.server.repository.BlogContentRepository;
import com.zhongkouwei.blog.server.repository.BlogRepository;
import com.zhongkouwei.blog.server.util.RedisUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

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

    /**
     * 线程阻塞队列
     */
    private static BlockingQueue BlockingQueue = new ArrayBlockingQueue(10);

    /**
     * 新增博客楼层操作时，将该博客的相关信息进行修改的线程池
     */
    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 1, TimeUnit.MINUTES, BlockingQueue);


    @Transactional(rollbackFor = Exception.class)
    public void   addBlog2(AddBlogDTO addBlogDTO, Integer userId) {
        Date createTime = new Date();
        //保存博客内容部分
        BlogContent blogContent = new BlogContent();
        Floor section = new Floor(addBlogDTO.getContent(), userId, "", createTime);
        blogContent.setSection(section);
        BlogContent contentSaved = blogContentRepository.save(blogContent);
        //保存博客标题等信息
        Blog blog = new Blog();
        blog.setBlogId(contentSaved.getBlogId());
        blog.setAuthor(userId);
        blog.setCreateTime(createTime);
        blog.setBlogName(addBlogDTO.getBlogName());
        blog.setBlogType(addBlogDTO.getBlogType());
        blogRepository.save(blog);
        String key = BlogType.getBlogKey(blog.getBlogType());
        redisUtil.saveBlog(key, blog);
    }

    @Transactional(rollbackFor = Exception.class)
    public String addBlog(BlogDTO blogDTO) {
        validatorService.validate(blogDTO,BlogGroup.insertBlog.class);
        Blog blog=new Blog();
        BeanUtils.copyProperties(blogDTO,blog);
        Blog blogSaved=blogRepository.save(blog);
        BlogContent blogContent=new BlogContent();
        blogContent.setBlogId(blogSaved.getBlogId());
        blogContent.setSections(blogDTO.getFloors());
        blogContentRepository.save(blogContent);
        return blogSaved.getBlogId();
    }

    public void addSection(Floor section, String blogId) {
        BlogContent blogContent = blogContentRepository.findOne(blogId);
        blogContent.setSection(section);
        blogContentRepository.save(blogContent);
        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Blog blog = blogRepository.findOne(blogId);
                if (blog != null) {
                    Integer sections = blog.getBlogNums() + 1;
                    blog.setBlogNums(sections);
                    blog.setLastModifeTime(new Date());
                    blogRepository.save(blog);
                }
            }
        });

    }

    public Page<Blog> getBlogs(Pageable pageable, Criteria criteria) {
        Query query = new Query(criteria);
        query.with(pageable);
        List<Blog> blogList = mongoTemplate.find(query, Blog.class);
        Long count = mongoTemplate.count(query, Blog.class);
        Page<Blog> pages = new PageImpl<>(blogList, pageable, count);
        return pages;
    }

    public BlogDTO getBlogDto(String blogId) {
        BlogContent blogContent = blogContentRepository.findOne(blogId);
        Blog blog = blogRepository.findOne(blogId);
        BlogDTO blogDTO = new BlogDTO();
        if (blogContent != null) {
            blogDTO.setFloors(blogContent.getSections());
        }
        if (blog != null) {
            BeanUtils.copyProperties(blog, blogDTO);
        }
        return blogDTO;

    }


}
