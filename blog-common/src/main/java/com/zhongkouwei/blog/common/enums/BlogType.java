package com.zhongkouwei.blog.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BlogType {
    MOVIE((byte)1,"MOVIE:"),
    CARTOON((byte)2,"CARTOON:"),
    CHAT((byte)3,"CHAT:"),
    GOOD((byte)4,"GOOD:");

    private Byte blogType;
    private String keyName;

    public static String getBlogKey(Byte blogType){
        for (BlogType blog:BlogType.values()){
            if(blogType.equals(blog.blogType)){
                return blog.keyName;
            }
        }
        return null;
    }


}
