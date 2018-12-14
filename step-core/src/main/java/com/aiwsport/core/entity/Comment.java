package com.aiwsport.core.entity;

public class Comment {
    private Integer id;

    private Integer userid;

    private Integer stroyid;

    private Integer templateid;

    private String content;

    private String createtime;

    private String modifytime;

    private String status;

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

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getModifytime() {
        return modifytime;
    }

    public void setModifytime(String modifytime) {
        this.modifytime = modifytime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTemplateid() {
        return templateid;
    }

    public void setTemplateid(Integer templateid) {
        this.templateid = templateid;
    }
}