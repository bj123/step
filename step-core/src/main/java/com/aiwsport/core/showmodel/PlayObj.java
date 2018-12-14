package com.aiwsport.core.showmodel;

import com.aiwsport.core.entity.Story;

import java.util.List;

/**
 * Created by yangjian9 on 2018/12/14.
 */
public class PlayObj {

    private List<Story> stories;

    private Story currentStory;

    public List<Story> getStories() {
        return stories;
    }

    public void setStories(List<Story> stories) {
        this.stories = stories;
    }

    public Story getCurrentStory() {
        return currentStory;
    }

    public void setCurrentStory(Story currentStory) {
        this.currentStory = currentStory;
    }
}
