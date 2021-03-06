package com.aiwsport.core.showmodel;

import com.aiwsport.core.entity.Template;
import com.aiwsport.core.entity.User;


import java.util.List;
import java.util.Map;

/**
 * Created by yangjian9 on 2018/11/26.
 */
public class InitObj {

    List<Template> banners;

    List<Template> today_list;

    List<Template> parenting_list;

    List<Template> recommend_list;

    Map<String, String> showConfig;

    User user;


    public List<Template> getBanners() {
        return banners;
    }

    public void setBanners(List<Template> banners) {
        this.banners = banners;
    }

    public List<Template> getToday_list() {
        return today_list;
    }

    public void setToday_list(List<Template> today_list) {
        this.today_list = today_list;
    }

    public List<Template> getParenting_list() {
        return parenting_list;
    }

    public void setParenting_list(List<Template> parenting_list) {
        this.parenting_list = parenting_list;
    }

    public List<Template> getRecommend_list() {
        return recommend_list;
    }

    public void setRecommend_list(List<Template> recommend_list) {
        this.recommend_list = recommend_list;
    }

    public Map<String, String> getShowConfig() {
        return showConfig;
    }

    public void setShowConfig(Map<String, String> showConfig) {
        this.showConfig = showConfig;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
