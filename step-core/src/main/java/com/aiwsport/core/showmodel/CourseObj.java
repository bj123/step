package com.aiwsport.core.showmodel;

import com.aiwsport.core.entity.Template;

import java.util.List;

/**
 * Created by yangjian9 on 2018/11/28.
 */
public class CourseObj {

    List<Template> banners;

    List<Template> news;

    List<Template> all;

    public List<Template> getBanners() {
        return banners;
    }

    public void setBanners(List<Template> banners) {
        this.banners = banners;
    }

    public List<Template> getNews() {
        return news;
    }

    public void setNews(List<Template> news) {
        this.news = news;
    }

    public List<Template> getAll() {
        return all;
    }

    public void setAll(List<Template> all) {
        this.all = all;
    }
}
