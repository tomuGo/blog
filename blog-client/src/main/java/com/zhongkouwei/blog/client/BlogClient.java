package com.zhongkouwei.blog.client;

import com.zhongkouwei.blog.common.BlogConstants;
import com.zhongkouwei.blog.common.dto.BlogDTO;
import com.zhongkouwei.blog.common.model.Blog;
import com.zhongkouwei.blog.common.model.Floor;
import com.zhongkouwei.user.common.model.ResultSub;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = BlogConstants.APP_PATH)
public interface BlogClient {

    @RequestMapping(value = BlogConstants.APP_PATH + "blog", method = RequestMethod.POST)
    ResultSub<String> addBlog(@RequestBody BlogDTO blogDTO);

    @RequestMapping(value = BlogConstants.APP_PATH + "blog", method = RequestMethod.GET)
    ResultSub<Page<Blog>> getBlogs(@RequestParam("pageNumber") Integer pageNumber,
                                   @RequestParam("pageSize") Integer pageSize,
                                   @RequestParam(value = "orderName", required = false) String orderName,
                                   @RequestParam(value = "orderType", required = false) String orderType,
                                   @RequestParam(value = "userId", required = false) Integer userId,
                                   @RequestParam(value = "blogType", required = false) Byte blogType,
                                   @RequestParam(value = "boutique", required = false) Byte boutique,
                                   @RequestParam(value = "blogName", required = false) String blogName);

    @RequestMapping(value = BlogConstants.APP_PATH + "blog/{id}/addSection", method = RequestMethod.POST)
    ResultSub<Boolean> addSection(@PathVariable("id") String blogId, @RequestBody Floor section);

    @RequestMapping(value = BlogConstants.APP_PATH + "blog/{id}", method = RequestMethod.GET)
    ResultSub<BlogDTO> getBlogContent(@PathVariable("id") String blogId);


}
