package com.aiwsport.core.showmodel;

import com.aiwsport.core.entity.Template;


import java.util.List;

/**
 * Created by yangjian9 on 2018/11/26.
 */
public class InitObj {

    List<Template> banners;

    List<Template> today_list;

    List<Template> parenting_list;


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
}
