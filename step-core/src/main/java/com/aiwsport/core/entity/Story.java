package com.aiwsport.core.entity;

public class Story {
    private Integer id;

    private Integer templateid;

    private Integer sort;

    private String isaudition;

    private String storyname;

    private String storydesc;

    private String storyicon;

    private String storytime;

    private Integer playcount;

    private String contenturl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTemplateid() {
        return templateid;
    }

    public void setTemplateid(Integer templateid) {
        this.templateid = templateid;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getIsaudition() {
        return isaudition;
    }

    public void setIsaudition(String isaudition) {
        this.isaudition = isaudition;
    }

    public String getStoryname() {
        return storyname;
    }

    public void setStoryname(String storyname) {
        this.storyname = storyname;
    }

    public String getStorydesc() {
        return storydesc;
    }

    public void setStorydesc(String storydesc) {
        this.storydesc = storydesc;
    }

    public String getStoryicon() {
        return storyicon;
    }

    public void setStoryicon(String storyicon) {
        this.storyicon = storyicon;
    }

    public String getStorytime() {
        return storytime;
    }

    public void setStorytime(String storytime) {
        this.storytime = storytime;
    }

    public Integer getPlaycount() {
        return playcount;
    }

    public void setPlaycount(Integer playcount) {
        this.playcount = playcount;
    }

    public String getContenturl() {
        return contenturl;
    }

    public void setContenturl(String contenturl) {
        this.contenturl = contenturl;
    }
}