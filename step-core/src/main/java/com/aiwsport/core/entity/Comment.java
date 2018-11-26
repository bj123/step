package com.aiwsport.core.entity;

public class Comment {
    private Integer id;

    private Integer userid;

    private Integer stroyid;

    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getStroyid() {
        return stroyid;
    }

    public void setStroyid(Integer stroyid) {
        this.stroyid = stroyid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}