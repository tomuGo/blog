package com.zhongkouwei.blog.server.model;

public enum BlogType {
    MOVIE((byte)1,"MOVIE:"),
    CARTOON((byte)2,"CARTOON:"),
    CHAT((byte)3,"CHAT:"),
    GOOD((byte)4,"GOOD:");

    BlogType(Byte blogType, String keyName) {
        this.blogType = blogType;
        this.keyName = keyName;
    }

    public static String getBlogKey(Byte blogType){
        for (BlogType blog:BlogType.values()){
            if(blogType.equals(blog.blogType)){
                return blog.keyName;
            }
        }
        return null;
    }

    private Byte blogType;
    private String keyName;

    public Byte getBlogType() {
        return blogType;
    }

    public void setBlogType(Byte blogType) {
        this.blogType = blogType;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }
}
