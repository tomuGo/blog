package com.zhongkouwei.blog.server.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Theme {
    MOVIE(1,"电影"),
    CANTOON(2,"漫画"),
    TALK(3,"随便聊聊"),;

    public Integer themeId;

    public String themeName;
}
