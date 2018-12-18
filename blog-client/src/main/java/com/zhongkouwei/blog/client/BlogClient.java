package com.zhongkouwei.blog.client;

import com.zhongkouwei.blog.common.BlogConstants;
import com.zhongkouwei.blog.common.dto.BlogDTO;
import com.zhongkouwei.blog.common.model.Blog;
import com.zhongkouwei.blog.common.model.Floor;
import com.zhongkouwei.blog.common.model.PageInfo;
import com.zhongkouwei.user.common.model.ResultSub;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = BlogConstants.APP)
public interface BlogClient {

    @RequestMapping(value = BlogConstants.BLOG_URL + "blog", method = RequestMethod.POST)
    ResultSub<String> addBlog(@RequestBody BlogDTO blogDTO);

    @RequestMapping(value = BlogConstants.BLOG_URL + "blog", method = RequestMethod.GET)
    ResultSub<PageInfo<Blog>> getBlogs(@RequestParam("pageNumber") Integer pageNumber,
                                       @RequestParam("pageSize") Integer pageSize,
                                       @RequestParam(value = "orderName", required = false) String orderName,
                                       @RequestParam(value = "orderType", required = false) String orderType,
                                       @RequestParam(value = "userId", required = false) Integer userId,
                                       @RequestParam(value = "blogType", required = false) Byte blogType,
                                       @RequestParam(value = "boutique", required = false) Byte boutique,
                                       @RequestParam(value = "blogName", required = false) String blogName);

    @RequestMapping(value = BlogConstants.BLOG_URL + "blog/{id}/addSection", method = RequestMethod.POST)
    ResultSub<Integer> addSection(@PathVariable("id") String blogId, @RequestBody Floor section);

    @RequestMapping(value = BlogConstants.BLOG_URL + "blog/{id}", method = RequestMethod.GET)
    ResultSub<BlogDTO> getBlogContent(@PathVariable("id") String blogId);


}
