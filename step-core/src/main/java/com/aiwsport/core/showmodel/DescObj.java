package com.aiwsport.core.showmodel;

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

    private boolean is_buy;

    private List<Story> stories;


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

    public boolean is_buy() {
        return is_buy;
    }

    public void setIs_buy(boolean is_buy) {
        this.is_buy = is_buy;
    }

    public List<Story> getStories() {
        return stories;
    }

    public void setStories(List<Story> stories) {
        this.stories = stories;
    }
}
