package com.aiwsport.core.showmodel;

import com.aiwsport.core.entity.CommentBean;
import com.aiwsport.core.entity.Introduction;
import com.aiwsport.core.entity.Story;
import com.aiwsport.core.entity.Template;

import java.util.List;
import java.util.Map;

/**
 * Created by yangjian9 on 2018/12/6.
 */
public class DescObj {

    private Template album;

    private String shareReward;

    private Map<String, String> showConfig;

    private List<Introduction> introductions;

    private String isbuy;

    private List<Story> stories;

    private List<CommentBean> comments;


    public Template getAlbum() {
        return album;
    }

    public void setAlbum(Template album) {
        this.album = album;
    }

    public Map<String, String> getShowConfig() {
        return showConfig;
    }

    public void setShowConfig(Map<String, String> showConfig) {
        this.showConfig = showConfig;
    }

    public String getShareReward() {
        return shareReward;
    }

    public void setShareReward(String shareReward) {
        this.shareReward = shareReward;
    }

    public List<Introduction> getIntroductions() {
        return introductions;
    }

    public void setIntroductions(List<Introduction> introductions) {
        this.introductions = introductions;
    }

    public String getIsbuy() {
        return isbuy;
    }

    public void setIsbuy(String isbuy) {
        this.isbuy = isbuy;
    }

    public List<Story> getStories() {
        return stories;
    }

    public void setStories(List<Story> stories) {
        this.stories = stories;
    }

    public List<CommentBean> getComments() {
        return comments;
    }

    public void setComments(List<CommentBean> comments) {
        this.comments = comments;
    }
}
