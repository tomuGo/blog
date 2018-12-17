package com.zhongkouwei.blog.server.controller;

import com.zhongkouwei.blog.common.BlogConstants;
import com.zhongkouwei.blog.common.dto.BlogDTO;
import com.zhongkouwei.blog.common.model.Blog;
import com.zhongkouwei.blog.common.model.Floor;
import com.zhongkouwei.blog.server.repository.BlogContentRepository;
import com.zhongkouwei.blog.server.repository.BlogRepository;
import com.zhongkouwei.blog.server.service.BlogService;
import com.zhongkouwei.blog.server.service.MongoService;
import com.zhongkouwei.user.common.model.ResultSub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping(value = BlogConstants.APP_PATH)
public class BlogController {

    @Autowired
    BlogService blogService;
    @Autowired
    BlogRepository blogRepository;
    @Autowired
    BlogContentRepository blogContentRepository;

    @RequestMapping(value = "blog", method = RequestMethod.POST)
    public ResultSub<String> addBlog(@RequestBody BlogDTO blogDTO) {
        String blogId=blogService.addBlog(blogDTO);
        return new ResultSub<>(blogId);
    }

    @RequestMapping(value = "blog", method = RequestMethod.GET)
    public ResultSub<Page<Blog>> getBlogs(@RequestParam("pageNumber") Integer pageNumber,
                                          @RequestParam("pageSize") Integer pageSize,
                                          @RequestParam(value = "orderName", required = false) String orderName,
                                          @RequestParam(value = "orderType", required = false) String orderType,
                                          @RequestParam(value = "userId", required = false) Integer userId,
                                          @RequestParam(value = "blogType", required = false) Byte blogType,
                                          @RequestParam(value = "boutique", required = false) Byte boutique,
                                          @RequestParam(value = "blogName", required = false) String blogName) {

        Criteria criteria = MongoService.convertCriteria(blogName, blogType, userId, boutique);
        Pageable pageable = MongoService.convertPageable(pageNumber-1, pageSize, orderName, orderType);
        Page<Blog> blogs = blogService.getBlogs(pageable,criteria);
        return new ResultSub<>(blogs);
    }

    @RequestMapping(value = "blog/{id}/addSection", method = RequestMethod.POST)
    public ResultSub<Boolean> addSection(@PathVariable("id") String blogId, @RequestBody Floor section) {
        section.setCreateTime(new Date());
        blogService.addSection(section, blogId);
        return new ResultSub<>(Boolean.TRUE);
    }

    @RequestMapping(value = "blog/{id}", method = RequestMethod.GET)
    public ResultSub<BlogDTO> getBlogContent(@PathVariable("id") String blogId) {
        BlogDTO blog = blogService.getBlogDto(blogId);
        return new ResultSub<>(blog);
    }

}
